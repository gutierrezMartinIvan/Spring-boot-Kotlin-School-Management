package ar.com.school.management.utils

import ar.com.school.management.models.entity.UserEntity
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class Mapper {
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var mm: ModelMapper

    fun <S,D> map(source: S, destinationType: Class<D>): D =
         when(val destination = mm.map(source, destinationType)) {
            is SubjectResponse -> fixNullSubjectResponse(destination)
            is TeacherResponse -> fixNullTeacherResponse(destination)
            is CareerResponse -> fixNullCareerResponse(destination)
            is StudentResponse -> fixNullStudentResponse(destination)
            else -> destination

    }

    fun <S, D> mapLists(sources: List<S>, destinationType: Class<D>): List<D> = sources.map { source -> map(source, destinationType) }

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

    fun updateAdminOrModerator(entity2Update: UserEntity, updatedRequest: UserRequest) {
        if (updatedRequest.name != null)
            entity2Update.name = updatedRequest.name
        if (updatedRequest.email != null)
            entity2Update.email = updatedRequest.email
        if (updatedRequest.phone != null)
            entity2Update.phone = updatedRequest.phone
        if (updatedRequest.surname != null)
            entity2Update.surname = updatedRequest.surname
        if (updatedRequest.pw != null)
            entity2Update.pw = passwordEncoder.encode(updatedRequest.pw)
    }


    //fun <S, D>update(source: S, update: Class<D>): D = map()
}