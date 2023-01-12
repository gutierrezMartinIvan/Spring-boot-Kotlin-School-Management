package ar.com.school.management.models.entity

import jakarta.persistence.*

@Entity
@Table(name = "careers")
class CareerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var name: String,

    var description: String?,

    @ManyToMany
    @JoinTable(
        name = "career_subject",
        joinColumns = [JoinColumn(name = "career_id")],
        inverseJoinColumns = [JoinColumn(name = "subject_id")]
    )
    var subjects: MutableList<SubjectEntity>?,

    @OneToMany(mappedBy = "careerId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var students: MutableList<StudentEntity>?,

    @ManyToMany
    @JoinTable(
        name = "career_teacher",
        joinColumns = [JoinColumn(name = "career_id")],
        inverseJoinColumns = [JoinColumn(name = "teacher_id")]
    )
    var teachers: MutableList<TeacherEntity>? = mutableListOf(),
)