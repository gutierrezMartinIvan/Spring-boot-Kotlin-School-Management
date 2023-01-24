package ar.com.school.management.utils

import ar.com.school.management.exception.AlreadyRegisteredException
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.repository.ManagerRepository
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class VerifyIfUserIsAlreadyRegistered {
    @Autowired
    private lateinit var managerRepository: ManagerRepository
    @Autowired
    private lateinit var teacherRepository: TeacherRepository
    @Autowired
    private lateinit var studentRepository: StudentRepository

    fun verify(request: UserRequest) {
        verifyEmail(request.email!!)
        verifyPhone(request.phone!!)
        verifySsn(request.socialSecurityNumber!!)
    }

    private fun verifyEmail(email: String) {
        managerRepository.findByEmail(email)
            .ifPresent { throw AlreadyRegisteredException("The email is already in use") }
        studentRepository.findByEmail(email)
            .ifPresent { throw AlreadyRegisteredException("The email is already in use") }
        teacherRepository.findByEmail(email)
            .ifPresent { throw AlreadyRegisteredException("The email is already in use") }
    }

    private fun verifyPhone(phone: Int) {
        managerRepository.findByPhone(phone)
            .ifPresent { throw AlreadyRegisteredException("The phone is already in use") }
        studentRepository.findByPhone(phone)
            .ifPresent { throw AlreadyRegisteredException("The phone is already in use") }
        teacherRepository.findByPhone(phone)
            .ifPresent { throw AlreadyRegisteredException("The phone is already in use") }
    }

    private fun verifySsn(socialSecurityNumber: Int) {
        managerRepository.findBySocialSecurityNumber(socialSecurityNumber)
            .ifPresent { throw AlreadyRegisteredException("The user is already registered") }
        studentRepository.findBySocialSecurityNumber(socialSecurityNumber)
            .ifPresent { throw AlreadyRegisteredException("The user is already registered") }
        teacherRepository.findBySocialSecurityNumber(socialSecurityNumber)
            .ifPresent { throw AlreadyRegisteredException("The user is already registered") }
    }
}