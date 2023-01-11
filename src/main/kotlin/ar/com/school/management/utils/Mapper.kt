package ar.com.school.management.utils

import ar.com.school.management.models.entity.CareerEntity
import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.request.StudentRequest
import ar.com.school.management.models.response.CareerResponse
import ar.com.school.management.models.response.StudentResponse
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Mapper {

    @Autowired
    private lateinit var modelMapper: ModelMapper

    fun studentEntity2Dto(entity: StudentEntity): StudentResponse = modelMapper.map(entity, StudentResponse::class.java)
    fun studentDto2Entity(request: StudentRequest): StudentEntity = modelMapper.map(request, StudentEntity::class.java)
    fun careerDto2Entity(request: CareerRequest): CareerEntity = modelMapper.map(request, CareerEntity::class.java)
    fun careerEntity2Dto(entity: CareerEntity): CareerResponse = modelMapper.map(entity, CareerResponse::class.java)


}