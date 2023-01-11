package ar.com.school.management.service.impl

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
import java.util.Optional

@Service
class StudentServiceImpl: StudentService {

    @Autowired
    private lateinit var repository: StudentRepository

    @Autowired
    private lateinit var mapper: Mapper

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    override fun save(request: StudentRequest): StudentResponse {
        var entity: Optional<StudentEntity> = repository.findBySocialSecurityNumber(request.socialSecurityNumber)
        if (entity.isPresent)
            throw UserRegisteredException("The user is already registered")

        var entitySave: StudentEntity = mapper.studentDto2Entity(request)
        entitySave.password = passwordEncoder.encode(entitySave.password)
        var response = repository.save(entitySave)

        return mapper.studentEntity2Dto(response)
    }
}