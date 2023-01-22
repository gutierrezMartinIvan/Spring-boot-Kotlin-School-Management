package ar.com.school.management.service.impl

import ar.com.school.management.config.security.JwtService
import ar.com.school.management.exception.InvalidRoleException
import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.entity.ManagerEntity
import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.UserResponse
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.TeacherRepository
import ar.com.school.management.repository.ManagerRepository
import ar.com.school.management.service.ManagementService
import ar.com.school.management.utils.Mapper
import ar.com.school.management.utils.Role
import ar.com.school.management.utils.VerifyIfUserIsAlreadyRegistered
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ManagementServiceImpl: ManagementService {
    @Autowired
    private lateinit var mapper: Mapper
    @Autowired
    private lateinit var managerRepository: ManagerRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var jwtService: JwtService
    @Autowired
    private lateinit var verify: VerifyIfUserIsAlreadyRegistered

    override fun registerAdminOrModerator(request: UserRequest, role: String): UserResponse {
        verify.verify(request)
        val userEntity = mapper.map(request, ManagerEntity::class.java)
        when(role.lowercase()){
            "admin" -> userEntity.role = Role.ADMIN
            "moderator" -> userEntity.role = Role.MODERATOR
            else -> throw InvalidRoleException("Invalid Role Name: $role")
        }
        userEntity.pw = passwordEncoder.encode(userEntity.pw)
        return mapper.map(managerRepository.save(userEntity), UserResponse::class.java)
    }

    override fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.pw
            )
        )
        val user = managerRepository.findByEmail(request.email).orElseThrow{ NotFoundException("User Not Found") }
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }

    override fun getUser(ssNumber: Int): UserResponse =
        mapper.map(managerRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("User Not Found") }, UserResponse::class.java)

    override fun getAllAdmins(role: String): List<UserResponse> =
        when(role.lowercase()){
            "admin" -> mapper.mapLists(managerRepository.findAllByRole(Role.ADMIN), UserResponse::class.java)
            "moderator" -> mapper.mapLists(managerRepository.findAllByRole(Role.MODERATOR), UserResponse::class.java)
            else -> mapper.mapLists(managerRepository.findAll(), UserResponse::class.java)
        }

    override fun updateUser(ssNumber: Int, request: UserRequest): UserResponse {
        var userEntity = managerRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The admin with the social security number: $ssNumber does not exists!") }
        mapper.updateUser(userEntity, request)
        return mapper.map(managerRepository.save(userEntity), UserResponse::class.java)
    }

    override fun deleteModerator(ssNumber: Int) {
        var userEntity = managerRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The admin with the social security number: $ssNumber does not exists!") }
        managerRepository.delete(userEntity)
    }
}