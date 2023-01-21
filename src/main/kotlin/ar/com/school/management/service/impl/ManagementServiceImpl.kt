package ar.com.school.management.service.impl

import ar.com.school.management.config.security.JwtService
import ar.com.school.management.exception.InvalidRoleException
import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.entity.UserEntity
import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.UserResponse
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.TeacherRepository
import ar.com.school.management.repository.UserRepository
import ar.com.school.management.service.ManagementService
import ar.com.school.management.utils.Mapper
import ar.com.school.management.utils.Role
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
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var studentRepository: StudentRepository
    @Autowired
    lateinit var teacherRepository: TeacherRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var jwtService: JwtService


    override fun registerAdminOrModerator(request: UserRequest, role: String): UserResponse {
        verifyIfAlreadyRegistered(request.socialSecurityNumber)
        val userEntity = mapper.map(request, UserEntity::class.java)
        when(role.lowercase()){
            "admin" -> userEntity.role = Role.ADMIN
            "moderator" -> userEntity.role = Role.MODERATOR
            else -> throw InvalidRoleException("Invalid Role Name: $role")
        }
        userEntity.pw = passwordEncoder.encode(userEntity.pw)
        return mapper.map(userRepository.save(userEntity), UserResponse::class.java)
    }

    override fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.pw
            )
        )
        val user = userRepository.findByEmail(request.email).orElseThrow{ NotFoundException("User Not Found") }
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }

    override fun getUser(ssNumber: Int): UserResponse =
        mapper.map(userRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("User Not Found") }, UserResponse::class.java)

    override fun getAllAdmins(role: String): List<UserResponse> =
        when(role.lowercase()){
            "admin" -> mapper.mapLists(userRepository.findAllByRole(Role.ADMIN), UserResponse::class.java)
            "moderator" -> mapper.mapLists(userRepository.findAllByRole(Role.MODERATOR), UserResponse::class.java)
            else -> mapper.mapLists(userRepository.findAll(), UserResponse::class.java)
        }

    override fun updateUser(ssNumber: Int, request: UserRequest): UserResponse {
        var userEntity = userRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The admin with the social security number: $ssNumber does not exists!") }
        mapper.updateAdminOrModerator(userEntity, request)
        return mapper.map(userRepository.save(userEntity), UserResponse::class.java)
    }

    override fun deleteModerator(ssNumber: Int) {
        var userEntity = userRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The admin with the social security number: $ssNumber does not exists!") }
        userRepository.delete(userEntity)
    }

    private fun verifyIfAlreadyRegistered(socialSecurityNumber: Int?) {
        if (userRepository.findBySocialSecurityNumber(socialSecurityNumber!!).isPresent)
            throw UserRegisteredException("The user is already registered")
        if (studentRepository.findBySocialSecurityNumber(socialSecurityNumber).isPresent)
            throw UserRegisteredException("The user is already registered as student")
        if (teacherRepository.findBySocialSecurityNumber(socialSecurityNumber).isPresent )
            throw UserRegisteredException("The user is already registered as teacher")
    }
}