package ar.com.school.management.service.impl

import ar.com.school.management.config.security.JwtService
import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.entity.UserEntity
import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.UserResponse
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
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var jwtService: JwtService


    override fun registerAdminOrModerator(request: UserRequest, role: String): UserResponse {
        if (request.socialSecurityNumber?.let { userRepository.findBySocialSecurityNumber(it).isPresent } !!)
            throw UserRegisteredException("The user is already registered")
        val userEntity = mapper.map(request, UserEntity::class.java)
        userEntity.role = if (role.lowercase() == "admin") Role.ADMIN else Role.MODERATOR
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
}