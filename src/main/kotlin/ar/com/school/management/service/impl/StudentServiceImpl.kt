package ar.com.school.management.service.impl

import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.TeacherRepository
import ar.com.school.management.repository.ManagerRepository
import ar.com.school.management.service.StudentService
import ar.com.school.management.utils.Mapper
import ar.com.school.management.utils.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl : StudentService {
    @Autowired
    private lateinit var managerRepository: ManagerRepository
    @Autowired
    private lateinit var teacherRepository: TeacherRepository
    @Autowired
    private lateinit var repository: StudentRepository
    @Autowired
    private lateinit var mapper: Mapper
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun save(request: UserRequest): StudentResponse {
        verifyIfAlreadyRegistered(request.socialSecurityNumber)
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
            .orElseThrow { NotFoundException("The admin with the social security number: $ssNumber does not exists!") }
        mapper.updateUser(studentEntity, request)
        return mapper.map(repository.save(studentEntity), StudentResponse::class.java)
    }

    override fun deleteStudent(ssNumber: Int) {
        var studentEntity = repository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The admin with the social security number: $ssNumber does not exists!") }
        repository.delete(studentEntity)
    }

    private fun verifyIfAlreadyRegistered(socialSecurityNumber: Int?) {
        if (managerRepository.findBySocialSecurityNumber(socialSecurityNumber!!).isPresent)
            throw UserRegisteredException("The user is already registered")
        if (repository.findBySocialSecurityNumber(socialSecurityNumber).isPresent)
            throw UserRegisteredException("The user is already registered as student")
        if (teacherRepository.findBySocialSecurityNumber(socialSecurityNumber).isPresent )
            throw UserRegisteredException("The user is already registered as teacher")
    }
}