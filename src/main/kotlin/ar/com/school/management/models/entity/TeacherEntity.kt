package ar.com.school.management.models.entity

import jakarta.persistence.*

@Entity
@Table(name = "teachers")
class TeacherEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    var id: Long? = null,

    @Column(name = "social_security_number")
    val socialSecurityNumber: Int,
    var name: String,
    var surname: String,
    var phone: Int,
    var email: String,

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    var careers: List<CareerEntity>? = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "teacher_subject",
        joinColumns = [JoinColumn(name = "teacher_id")],
        inverseJoinColumns = [JoinColumn(name = "subject_id")]
    )
    var subjects: List<SubjectEntity>? = mutableListOf()
)