package ar.com.school.management.utils

import ar.com.school.management.models.entity.CareerEntity
import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.entity.TeacherEntity
import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.request.StudentRequest
import ar.com.school.management.models.request.TeacherRequest
import ar.com.school.management.models.response.CareerResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.TeacherResponse
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Mapper {

    @Autowired
    private lateinit var mm: ModelMapper

    fun studentEntity2Dto(entity: StudentEntity): StudentResponse = mm.map(entity, StudentResponse::class.java)
    fun studentDto2Entity(request: StudentRequest): StudentEntity = mm.map(request, StudentEntity::class.java)
    fun careerDto2Entity(request: CareerRequest): CareerEntity = mm.map(request, CareerEntity::class.java)
    fun careerEntity2Dto(entity: CareerEntity): CareerResponse = mm.map(entity, CareerResponse::class.java)
    fun teacherDto2Entity(request: TeacherRequest): TeacherEntity = mm.map(request, TeacherEntity::class.java)

    fun teacherEntity2Dto(entity: TeacherEntity): TeacherResponse = mm.map(entity, TeacherResponse::class.java)


}