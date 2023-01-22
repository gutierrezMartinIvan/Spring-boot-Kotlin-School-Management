package ar.com.school.management.service.impl

import ar.com.school.management.config.security.JwtService
import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.entity.TeacherEntity
import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.TeacherResponse
import ar.com.school.management.repository.ManagerRepository
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.TeacherRepository
import ar.com.school.management.service.TeacherService
import ar.com.school.management.utils.Mapper
import ar.com.school.management.utils.Role
import ar.com.school.management.utils.VerifyIfUserIsAlreadyRegistered
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class TeacherServiceImpl: TeacherService {
    @Autowired
    private lateinit var teacherRepository: TeacherRepository
    @Autowired
    private lateinit var mapper: Mapper
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var verify: VerifyIfUserIsAlreadyRegistered
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var jwtService: JwtService
    override fun save(request: UserRequest): TeacherResponse {
        verify.verify(request)
        var entity = mapper.map(request, TeacherEntity::class.java)
        entity.role = Role.TEACHER
        entity.pw = passwordEncoder.encode(entity.pw)
        return mapper.map(teacherRepository.save(entity), TeacherResponse::class.java)
    }

    override fun getTeacherBySocialSecurityNumber(ssNumber: Int): TeacherResponse =
         mapper.map(teacherRepository.findBySocialSecurityNumber(ssNumber).orElseThrow {
            NotFoundException("The ssNumber: $ssNumber does not belong to any teacher registered")
        }, TeacherResponse::class.java)

    override fun getAllTeachers(): List<TeacherResponse> = mapper.mapLists(teacherRepository.findAll(), TeacherResponse::class.java)

    override fun updateTeacher(ssNumber: Int, request: UserRequest): TeacherResponse {
        var teacherEntity = teacherRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The teacher with the social security number: $ssNumber does not exists!") }
        mapper.updateUser(teacherEntity, request)
        return mapper.map(teacherRepository.save(teacherEntity), TeacherResponse::class.java)
    }

    override fun deleteTeacher(ssNumber: Int) {
        var teacherEntity = teacherRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The teacher with the social security number: $ssNumber does not exists!") }
        teacherRepository.delete(teacherEntity)
    }

    override fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.pw
            )
        )
        val user = teacherRepository.findByEmail(request.email).orElseThrow{ NotFoundException("User Not Found") }
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }
}