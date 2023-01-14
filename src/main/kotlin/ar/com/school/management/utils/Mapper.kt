package ar.com.school.management.utils

import ar.com.school.management.models.entity.CareerEntity
import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.models.entity.TeacherEntity
import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.request.StudentRequest
import ar.com.school.management.models.request.SubjectRequest
import ar.com.school.management.models.request.TeacherRequest
import ar.com.school.management.models.response.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class Mapper {

    @Autowired
    private lateinit var mm: ModelMapper

    fun studentDto2Entity(request: StudentRequest): StudentEntity = mm.map(request, StudentEntity::class.java)
    fun careerDto2Entity(request: CareerRequest): CareerEntity = mm.map(request, CareerEntity::class.java)
    fun teacherDto2Entity(request: TeacherRequest): TeacherEntity = mm.map(request, TeacherEntity::class.java)
    fun subjectDto2Entity(request: SubjectRequest): SubjectEntity = mm.map(request, SubjectEntity::class.java)

    fun <S,D> entity2Dto(source: S, destinationType: Class<D>): D {
        return when(val destination = mm.map(source, destinationType)) {
            is SubjectResponse -> fixNullSubjectResponse(destination)
            is TeacherResponse -> fixNullTeacherResponse(destination)
            is CareerResponse -> fixNullCareerResponse(destination)
            is StudentResponse -> fixNullStudentResponse(destination)
            else -> throw RuntimeException("testing")
        }
    }

    private fun <D> fixNullStudentResponse(destination: StudentResponse): D {
        destination.subjects = destination.subjects ?: mutableListOf()
        destination.career = destination.career ?: CareerSimpleResponse("Not defined yet")
        return destination as D
    }

    private fun <D> fixNullCareerResponse(destination: CareerResponse): D {
        destination.subjects = destination.subjects ?: mutableListOf()
        destination.students = destination.students ?: mutableListOf()
        return destination as D
    }

    private fun <D> fixNullTeacherResponse(destination: TeacherResponse): D {
       destination.subjects = destination.subjects ?: mutableListOf()
        return destination as D
    }

    private fun <D> fixNullSubjectResponse(destination: SubjectResponse): D {
        destination.students = destination.students ?: mutableListOf()
        destination.teachers = destination.teachers ?: mutableListOf()
        destination.careers = destination.careers ?: mutableListOf()
        return destination as D
    }


}