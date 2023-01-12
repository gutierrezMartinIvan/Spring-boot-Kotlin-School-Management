package ar.com.school.management.service.impl

import ar.com.school.management.exception.CareerRegisteredException
import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.UserRegisteredException
import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.response.CareerResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.repository.CareerRepository
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.service.CareerService
import ar.com.school.management.service.StudentService
import ar.com.school.management.utils.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CareerServiceImpl: CareerService {

    @Autowired
    private lateinit var repository: CareerRepository

    @Autowired
    private lateinit var mapper: Mapper

    @Autowired
    private lateinit var studentRepository: StudentRepository

    override fun save(request: CareerRequest): CareerResponse {
        var entity = request.name?.let { repository.findByName(it) }
        if (entity?.isPresent == true)
            throw CareerRegisteredException("The career is already registered!")

        var entitySave = mapper.careerDto2Entity(request)
        return mapper.careerEntity2Dto(repository.save(entitySave))
    }

    override fun signUpStudent2Career(careerId: Long, studentSsN: Int): StudentResponse {
       var studentEntity = studentRepository.findBySocialSecurityNumber(studentSsN)
           .orElseThrow {NotFoundException("The ssNumber: $studentSsN does not belong to any student registered")}
       var careerEntity = repository.findById(careerId)
           .orElseThrow { NotFoundException("The career id: $careerId does not belong to any career registered")  }
       if (careerEntity.students!!.contains(studentEntity))
           throw UserRegisteredException("The student is already signed up in this career")

       careerEntity.students!!.add(studentEntity)
       repository.save(careerEntity)
       studentEntity.careerId = careerEntity
       return mapper.studentEntity2Dto(studentRepository.save(studentEntity))
    }
}