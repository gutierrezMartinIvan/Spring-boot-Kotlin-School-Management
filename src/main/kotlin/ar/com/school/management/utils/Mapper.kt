package ar.com.school.management.utils

import ar.com.school.management.models.response.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Mapper {

    @Autowired
    private lateinit var mm: ModelMapper

    fun <S,D> map(source: S, destinationType: Class<D>): D {
        return when(val destination = mm.map(source, destinationType)) {
            is SubjectResponse -> fixNullSubjectResponse(destination)
            is TeacherResponse -> fixNullTeacherResponse(destination)
            is CareerResponse -> fixNullCareerResponse(destination)
            is StudentResponse -> fixNullStudentResponse(destination)
            else -> return destination
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