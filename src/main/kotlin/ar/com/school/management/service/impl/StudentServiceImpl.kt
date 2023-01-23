package ar.com.school.management.service.impl

import ar.com.school.management.config.security.JwtService
import ar.com.school.management.exception.InvalidPasswordException
import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.models.entity.StudentEntity
import ar.com.school.management.models.request.AuthenticationRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.models.response.AuthenticationResponse
import ar.com.school.management.models.response.StudentResponse
import ar.com.school.management.models.response.SubjectInfoResponseForStudent
import ar.com.school.management.repository.StudentRepository
import ar.com.school.management.repository.SubjectRepository
import ar.com.school.management.service.StudentService
import ar.com.school.management.utils.Mapper
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
class StudentServiceImpl : StudentService {
    @Autowired
    private lateinit var studentRepository: StudentRepository
    @Autowired
    private lateinit var subjectRepository: SubjectRepository
    @Autowired
    private lateinit var mapper: Mapper
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var jwtService: JwtService
    @Autowired
    private lateinit var verify: VerifyIfUserIsAlreadyRegistered
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    override fun save(request: UserRequest): StudentResponse {
        verify.verify(request)
        val entitySave = mapper.map(request, StudentEntity::class.java)
        entitySave.role = Role.STUDENT
        entitySave.pw = passwordEncoder.encode(entitySave.pw)
        return mapper.map(studentRepository.save(entitySave), StudentResponse::class.java)
    }

    override fun getStudentBySocialSecurityNumber(ssNumber: Int): StudentResponse =
        mapper.map(studentRepository.findBySocialSecurityNumber(ssNumber).orElseThrow{
            NotFoundException("The ssNumber: $ssNumber does not belong to any student registered")
        }, StudentResponse::class.java)

    override fun getAllStudents(): List<StudentResponse> = mapper.mapLists(studentRepository.findAll(), StudentResponse::class.java)
    override fun updateStudent(ssNumber: Int, request: UserRequest): StudentResponse {
        val studentEntity = studentRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The student with the social security number: $ssNumber does not exists!") }
        mapper.updateUser(studentEntity, request)
        return mapper.map(studentRepository.save(studentEntity), StudentResponse::class.java)
    }

    override fun deleteStudent(ssNumber: Int) {
        val studentEntity = studentRepository.findBySocialSecurityNumber(ssNumber)
            .orElseThrow { NotFoundException("The student with the social security number: $ssNumber does not exists!") }
        studentRepository.delete(studentEntity)
    }

    override fun logIn(request: AuthenticationRequest): AuthenticationResponse {
        val studentEntity = studentRepository.findByEmail(request.email).orElseThrow{ NotFoundException("Student Not Found") }
        if (!passwordEncoder.matches(request.pw, studentEntity.pw))
            throw InvalidPasswordException("The email or password is incorrect")
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.pw
            )
        )
        val jwtToken = jwtService.generateToken(studentEntity)
        return AuthenticationResponse(jwtToken)
    }

    override fun getSubjectStatus(subjectId: Long): SubjectInfoResponseForStudent {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val email  = jwtService.extractUsername(request.getHeader("Authorization").substring(7))
        val studentEntity = studentRepository.findByEmail(email).orElseThrow { NotFoundException("Student with email: $email not found") }
        val subjectEntity = subjectRepository.findById(subjectId).orElseThrow { NotFoundException("Subject with ID: $subjectId not found") }
        if (!studentEntity.subjects!!.contains(subjectEntity) || studentEntity.subjects!!.isEmpty())
            throw NotFoundException("The student ${studentEntity.name} does not have the subject ${subjectEntity.name}")
        var subjectResponse = SubjectInfoResponseForStudent()
        for (subject in studentEntity.subjects!!){
            if (subject.name == subjectEntity.name)
                subjectResponse = mapper.map(subject, SubjectInfoResponseForStudent::class.java)
        }
        return subjectResponse
    }
}