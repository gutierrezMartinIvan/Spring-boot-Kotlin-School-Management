package ar.com.school.management.service.impl

import ar.com.school.management.config.security.JwtService
import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.UserResponse
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.TeacherRepository
import ar.com.school.management.repository.ManagerRepository
import ar.com.school.management.service.StudentService
import ar.com.school.management.utils.Mapper
import ar.com.school.management.utils.Role
import ar.com.school.management.utils.VerifyIfUserIsAlreadyRegistered
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.net.http.HttpRequest

@Service
class StudentServiceImpl : StudentService {
    @Autowired
    private lateinit var repository: StudentRepository
    @Autowired
    private lateinit var mapper: Mapper
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var jwtService: JwtService
    @Autowired
    private lateinit var verify: VerifyIfUserIsAlreadyRegistered

    override fun save(request: UserRequest): StudentResponse {
        verify.verify(request)
        val entitySave = mapper.map(request, StudentEntity::class.java)
        entitySave.role = Role.STUDENT
        entitySave.pw = passwordEncoder.encode(entitySave.pw)
        return mapper.map(repository.save(entitySave), StudentResponse::class.java)
    }

    override fun getStudentBySocialSecurityNumber(ssNumber: Int): StudentResponse =
        mapper.map(repository.findBySocialSecurityNumber(ssNumber).orElseThrow{
            NotFoundException("The ssNumber: $ssNumber does not belong to any student registered")
        }, StudentResponse::class.java)

    override fun getAllStudents(): List<StudentResponse> = mapper.mapLists(repository.findAll(), StudentResponse::class.java)
    override fun updateStudent(ssNumber: Int, request: UserRequest): StudentResponse {
        var studentEntity = repository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The student with the social security number: $ssNumber does not exists!") }
        mapper.updateUser(studentEntity, request)
        return mapper.map(repository.save(studentEntity), StudentResponse::class.java)
    }

    override fun deleteStudent(ssNumber: Int) {
        var studentEntity = repository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The student with the social security number: $ssNumber does not exists!") }
        repository.delete(studentEntity)
    }
}