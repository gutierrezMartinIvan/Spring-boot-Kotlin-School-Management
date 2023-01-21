package ar.com.school.management.config

import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.request.SubjectRequest
import ar.com.school.management.models.request.UserRequest
import ar.com.school.management.service.CareerService
import ar.com.school.management.service.ManagementService
import ar.com.school.management.service.StudentService
import ar.com.school.management.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
@Component
class InitializerSeeder: CommandLineRunner {
    @Autowired
    private lateinit var managementService: ManagementService
    @Autowired
    private lateinit var studentService: StudentService
    @Autowired
    private lateinit var teacherService: StudentService
    @Autowired
    private lateinit var careerService: CareerService
    @Autowired
    private lateinit var subjectService: SubjectService

    override fun run(vararg args: String?) {
        createAdmins()
        createModerators()
        createStudents()
        createTeachers()
        createCareers()
        createSubjects()
    }

    private fun createSubjects() {
        subjectService.save(SubjectRequest("Introduction to Computer Science"))
        subjectService.save(SubjectRequest("Data Structures and Algorithms"))
        subjectService.save(SubjectRequest("Object-Oriented Programming"))
        subjectService.save(SubjectRequest("Database Systems"))
        subjectService.save(SubjectRequest("Operating Systems"))
        subjectService.save(SubjectRequest("Computer Networks"))
        subjectService.save(SubjectRequest("Software Engineering"))
        subjectService.save(SubjectRequest("Artificial Intelligence"))
        subjectService.save(SubjectRequest("Computer Architecture"))
    }

    private fun createCareers() {
        careerService.save(CareerRequest("Bachelor of Science in Computer Science",
            "This program focuses on the theoretical and practical aspects of computer science, including algorithms, programming languages, and software development."))
        careerService.save(CareerRequest("Bachelor of Science in Information Technology",
            "This program covers the design, implementation, and management of information systems and technology."))
        careerService.save(CareerRequest("Bachelor of Science in Cybersecurity",
            "This program focuses on the protection of computer systems, networks, and data from unauthorized access, use, disclosure, disruption, modification, or destruction."))
        careerService.save(CareerRequest("Bachelor of Science in Data Science",
            "This program covers the collection, storage, processing, and analysis of large data sets to extract insights and knowledge."))
        careerService.save(CareerRequest("Bachelor of Science in Artificial Intelligence",
            "This program covers the development and application of algorithms and computational models to simulate human intelligence in computers."))
    }

    private fun createTeachers() {
        teacherService.save(UserRequest(165878891, "Isabella", "Thompson", 55888999, "isabellathompson@email.com","p@ssw0rd16" ))
        teacherService.save(UserRequest(177999101, "Ethan", "Garcia", 559999000, "ethangarcia@email.com","p@ssw0rd17" ))
        teacherService.save(UserRequest(189100111, "Aria", "Martinez", 555112222, "ariamartinez@email.com","p@ssw0rd18" ))
        teacherService.save(UserRequest(190211121, "Noah", "Robinson", 52223333, "noahrobinson@email.com","p@ssw0rd19" ))
        teacherService.save(UserRequest(201321231, "Lily", "Clark", 555333444, "lilyclark@email.com","p@ssw0rd20" ))
    }

    private fun createStudents() {
        studentService.save(UserRequest(111223341, "Emily", "Thomas", 553334444, "emilythomas@email.com","p@ssw0rd11" ))
        studentService.save(UserRequest(122334451, "Madison", "Jackson", 554445555, "madisonjackson@email.com","p@ssw0rd12" ))
        studentService.save(UserRequest(132445561, "Matthew", "White", 55555666, "matthewwhite@email.com","p@ssw0rd13" ))
        studentService.save(UserRequest(143556671, "Olivia", "Harris", 555667777, "oliviaharris@email.com","p@ssw0rd14" ))
        studentService.save(UserRequest(154767781, "Jacob", "Martin", 557777888, "jacobmartin@email.com","p@ssw0rd15" ))
    }

    private fun createModerators() {
        managementService.registerAdminOrModerator(
            UserRequest(456789023, "Charlie", "Brown",
                555556666, "charliebrown@email.com","p@ssw0rd6" ), "moderator")
        managementService.registerAdminOrModerator(
            UserRequest(567890234, "Rachel", "Miller",
                557777888, "rachelmiller@email.com","p@ssw0rd7" ), "moderator")
        managementService.registerAdminOrModerator(
            UserRequest(678901234, "Mark", "Moore",
                559990000, "markmoore@email.com","p@ssw0rd8" ), "moderator")
        managementService.registerAdminOrModerator(
            UserRequest(789012345, "Lauren", "Taylor",
                555112222, "laurentaylor@email.com","p@ssw0rd9" ), "moderator")
        managementService.registerAdminOrModerator(
            UserRequest(890123456, "Adam", "Anderson",
                5222333, "adamanderson@email.com","p@ssw0rd10" ), "moderator")
    }

    private fun createAdmins() {
        managementService.registerAdminOrModerator(
            UserRequest(789456120, "Emily", "Smith",
                555123456, "emilysmith@email.com","p@ssw0rd1" ), "admin")
        managementService.registerAdminOrModerator(
            UserRequest(987654321, "John", "Doe",
                555111222, "johndoe@email.com","p@ssw0rd2" ), "admin")
        managementService.registerAdminOrModerator(
            UserRequest(123456789, "Jane", "Smith",
                555223333, "janesmith@email.com","p@ssw0rd3" ), "admin")
        managementService.registerAdminOrModerator(
            UserRequest(234567890, "Bob", "Johnson",
                555334444, "bobjohnson@email.com","p@ssw0rd4" ), "admin")
        managementService.registerAdminOrModerator(
            UserRequest(345678901, "Alice", "Williams",
                554445555, "alicewilliams@email.com","p@ssw0rd5" ), "admin")
    }
}