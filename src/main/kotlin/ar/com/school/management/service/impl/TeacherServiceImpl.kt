package ar.com.school.management.service.impl

import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.entity.TeacherEntity
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.TeacherResponse
import ar.com.school.management.repository.ManagerRepository
import ar.com.school.management.repository.StudentRepository
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
    private lateinit var managerRepository: ManagerRepository
    @Autowired
    private lateinit var teacherRepository: TeacherRepository
    @Autowired
    private lateinit var studentRepository: StudentRepository
    @Autowired
    private lateinit var mapper: Mapper
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun save(request: UserRequest): TeacherResponse {
        verifyIfAlreadyRegistered(request.socialSecurityNumber)
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

    private fun verifyIfAlreadyRegistered(socialSecurityNumber: Int?) {
        if (managerRepository.findBySocialSecurityNumber(socialSecurityNumber!!).isPresent)
            throw UserRegisteredException("The user is already registered as manager")
        if (studentRepository.findBySocialSecurityNumber(socialSecurityNumber).isPresent)
            throw UserRegisteredException("The user is already registered as student")
        if (teacherRepository.findBySocialSecurityNumber(socialSecurityNumber).isPresent )
            throw UserRegisteredException("The user is already registered!")
    }
}