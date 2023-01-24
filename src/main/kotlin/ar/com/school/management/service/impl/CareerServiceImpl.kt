package ar.com.school.management.service.impl

import ar.com.school.management.exception.CareerRegisteredException
import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.exception.AlreadyRegisteredException
import ar.com.school.management.models.entity.CareerEntity
import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.response.CareerResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.repository.CareerRepository
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.SubjectRepository
import ar.com.school.management.service.CareerService
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

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    override fun save(request: CareerRequest): CareerResponse {
        var entity = request.name?.let { repository.findByName(it) }
        if (entity?.isPresent == true)
            throw CareerRegisteredException("The career is already registered!")

        var entitySave = mapper.map(request, CareerEntity::class.java)
        return mapper.map(repository.save(entitySave), CareerResponse::class.java)
    }

    override fun signUpStudent2Career(careerId: Long, studentSsN: Int): StudentResponse {
       var studentEntity = studentRepository.findBySocialSecurityNumber(studentSsN)
           .orElseThrow {NotFoundException("The ssNumber: $studentSsN does not belong to any student registered")}
       var careerEntity = repository.findById(careerId)
           .orElseThrow { NotFoundException("The career id: $careerId does not belong to any career registered")  }
       if (studentEntity.career != null)
           throw AlreadyRegisteredException("The student is already signed up in a career")

       careerEntity.students!!.add(studentEntity)
       repository.save(careerEntity)
       studentEntity.career = careerEntity
       return mapper.map(studentRepository.save(studentEntity), StudentResponse::class.java)
    }

    override fun addSubject2Career(careerId: Long, subjectId: Long): CareerResponse {
        val subjectEntity = subjectRepository.findById(subjectId)
            .orElseThrow {NotFoundException("The ID: $subjectId does not belong to any subject registered")}
        val careerEntity = repository.findById(careerId)
            .orElseThrow { NotFoundException("The career id: $careerId does not belong to any career registered")  }
        if (careerEntity.subjects!!.contains(subjectEntity))
            throw AlreadyRegisteredException("The career ${careerEntity.name} already has the subject: ${subjectEntity.name}")

        careerEntity.subjects!!.add(subjectEntity)
        subjectEntity.careers!!.add(careerEntity)
        subjectRepository.save(subjectEntity)
        return mapper.map(repository.save(careerEntity), CareerResponse::class.java)
    }

    override fun getCareerById(id: Long): CareerResponse =
        mapper.map(repository.findById(id)
            .orElseThrow { NotFoundException("The id: $id does not belong to any career registered") }
            , CareerResponse::class.java)

    override fun getCareers(): List<CareerResponse> = mapper.mapLists(repository.findAll(), CareerResponse::class.java)
}