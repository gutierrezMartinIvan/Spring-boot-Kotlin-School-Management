package ar.com.school.management.service.impl

import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.request.StudentRequest
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.service.StudentService
import ar.com.school.management.utils.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl : StudentService {

    @Autowired
    private lateinit var repository: StudentRepository

    @Autowired
    private lateinit var mapper: Mapper

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    override fun save(request: StudentRequest): StudentResponse {
        if (request.socialSecurityNumber?.let { repository.findBySocialSecurityNumber(it).isPresent } !!)
            throw UserRegisteredException("The student is already registered")

        var entitySave = mapper.studentDto2Entity(request)
        entitySave.password = passwordEncoder.encode(entitySave.password)

        return mapper.entity2Dto(repository.save(entitySave), StudentResponse::class.java)
    }

    override fun getStudentBySocialSecurityNumber(ssNumber: Int): StudentResponse {
        return mapper.entity2Dto(repository.findBySocialSecurityNumber(ssNumber).orElseThrow{
            NotFoundException("The ssNumber: $ssNumber does not belong to any student registered")
        }, StudentResponse::class.java)
    }
}