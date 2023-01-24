package ar.com.school.management.service.impl

import ar.com.school.management.config.security.JwtService
import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.models.entity.CareerEntity
import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.models.entity.TeacherEntity
import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.StudentSubjectResponse
import ar.com.school.management.models.response.TeacherResponse
import ar.com.school.management.repository.*
import ar.com.school.management.service.StudentSubjectService
import ar.com.school.management.service.TeacherService
import ar.com.school.management.utils.Mapper
import ar.com.school.management.utils.Mark
import ar.com.school.management.utils.Role
import ar.com.school.management.utils.VerifyIfUserIsAlreadyRegistered
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Service
class TeacherServiceImpl: TeacherService {
    @Autowired
    private lateinit var teacherRepository: TeacherRepository
    @Autowired
    private lateinit var studentRepository: StudentRepository
    @Autowired
    private lateinit var subjectRepository: SubjectRepository
    @Autowired
    private lateinit var careerRepository: CareerRepository
    @Autowired
    private lateinit var mapper: Mapper
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var verify: VerifyIfUserIsAlreadyRegistered
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var jwtService: JwtService
    @Autowired
    private lateinit var studentSubjectService: StudentSubjectService

    override fun save(request: UserRequest): TeacherResponse {
        verify.verify(request)
        var entity = mapper.map(request, TeacherEntity::class.java)
        entity.role = Role.TEACHER
        entity.pw = passwordEncoder.encode(entity.pw)
        return mapper.map(teacherRepository.save(entity), TeacherResponse::class.java)
    }

    override fun getTeacherBySocialSecurityNumber(ssNumber: Int): TeacherResponse =
         mapper.map(teacherRepository.findBySocialSecurityNumber(ssNumber).orElseThrow {
            NotFoundException("The ssNumber: $ssNumber does not belong to any teacher registered")
        }, TeacherResponse::class.java)

    override fun getAllTeachers(): List<TeacherResponse> = mapper.mapLists(teacherRepository.findAll(), TeacherResponse::class.java)

    override fun updateTeacher(ssNumber: Int, request: UserRequest): TeacherResponse {
        var teacherEntity = teacherRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The teacher with the social security number: $ssNumber does not exists!") }
        mapper.updateUser(teacherEntity, request)
        return mapper.map(teacherRepository.save(teacherEntity), TeacherResponse::class.java)
    }

    override fun deleteTeacher(ssNumber: Int) {
        var teacherEntity = teacherRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The teacher with the social security number: $ssNumber does not exists!") }
        teacherRepository.delete(teacherEntity)
    }

    override fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.pw
            )
        )
        val user = teacherRepository.findByEmail(request.email).orElseThrow{ NotFoundException("User Not Found") }
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }

    override fun addMark2StudentInSubject(careerId: Long, subjectId: Long, studentSsn: Int, mark: String): StudentSubjectResponse {
        val studentEntity = studentRepository.findBySocialSecurityNumber(studentSsn)
            .orElseThrow { NotFoundException("The student with the social security number: $studentSsn does not exists!") }
        val subjectEntity = subjectRepository.findById(subjectId)
            .orElseThrow { NotFoundException("The subject with the ID: $subjectId does not exists!") }
        val careerEntity = careerRepository.findById(careerId)
            .orElseThrow { NotFoundException("The career with the ID: $careerId does not exists!") }
        addMark2StudentInSubjectAux(studentEntity, careerEntity, subjectEntity)
        return studentSubjectService.save(studentEntity, subjectEntity, mark)
    }

    private fun addMark2StudentInSubjectAux(student: StudentEntity, career: CareerEntity, subject: SubjectEntity) {
        val teacherEntity = getTeacherByEmailInToken()
        verifyIfIsNull(teacherEntity.subjects, subject.careers, student.career)
       if (!teacherEntity.subjects!!.contains(subject))
           throw NotFoundException("The student ${teacherEntity.name} ${teacherEntity.surname} doesn't have the subject ${subject.name}")
        if (!subject.careers!!.contains(career))
            throw NotFoundException("The career ${career.name} doesn't have the subject ${subject.name}")
        if (career.name != student.career?.name)
            throw NotFoundException("The student ${student.name} ${student.surname} is not in the career ${career.name}")
        if (!student.subjects!!.contains(subject))
            throw NotFoundException("The student ${student.name} ${student.surname} doesn't have the subject ${subject.name}")
    }

    private fun verifyIfIsNull(subjects: MutableList<SubjectEntity>?, careers: MutableList<CareerEntity>?, career: CareerEntity?) {
        if (subjects == null)
            throw NotFoundException("The teacher doesn't have any subject assigned yet")
        if (careers == null)
            throw NotFoundException("The subject is not assigned to any career yet")
        if (career == null)
            throw NotFoundException("The student is not in any career yet")
    }

    private fun getTeacherByEmailInToken(): TeacherEntity {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val email  = jwtService.extractUsername(request.getHeader("Authorization").substring(7))
        return teacherRepository.findByEmail(email)
            .orElseThrow { NotFoundException("The teacher with the email: $email does not exists!") }
    }
}