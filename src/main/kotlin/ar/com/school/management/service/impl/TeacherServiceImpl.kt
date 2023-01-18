package ar.com.school.management.service.impl

import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.entity.TeacherEntity
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.TeacherResponse
import ar.com.school.management.repository.TeacherRepository
import ar.com.school.management.service.TeacherService
import ar.com.school.management.utils.Mapper
import ar.com.school.management.utils.Role
import org.springframework.beans.factory.annotation.Autowired
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

    override fun save(request: UserRequest): TeacherResponse {
        if (request.socialSecurityNumber?.let { teacherRepository.findBySocialSecurityNumber(it).isPresent }!!)
            throw UserRegisteredException("The teacher is already registered!")
        var entity = mapper.map(request, TeacherEntity::class.java)
        entity.role = Role.TEACHER
        entity.pw = passwordEncoder.encode(entity.pw)
        return mapper.map(teacherRepository.save(entity), TeacherResponse::class.java)
    }

    override fun getTeacherBySocialSecurityNumber(ssNumber: Int): TeacherResponse =
         mapper.map(teacherRepository.findBySocialSecurityNumber(ssNumber).orElseThrow {
            NotFoundException("The ssNumber: $ssNumber does not belong to any teacher registered")
        }, TeacherResponse::class.java)
}