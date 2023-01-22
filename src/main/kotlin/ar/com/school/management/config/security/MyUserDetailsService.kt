package ar.com.school.management.config.security

import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.repository.ManagerRepository
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService: UserDetailsService {
    @Autowired
    private lateinit var managerRepository: ManagerRepository
    @Autowired
    private lateinit var studentRepository: StudentRepository
    @Autowired
    private lateinit var teacherRepository: TeacherRepository
    override fun loadUserByUsername(username: String?): UserDetails {
        val managerEntity = managerRepository.findByEmail(username!!)
        if (managerEntity.isPresent)
            return managerEntity.get()
        val studentEntity = studentRepository.findByEmail(username!!)
        if (studentEntity.isPresent)
            return studentEntity.get()
        val teacherEntity = teacherRepository.findByEmail(username!!)
        if (teacherEntity.isPresent)
            return teacherEntity.get()
        throw NotFoundException("User not found with the email: $username")
    }


}