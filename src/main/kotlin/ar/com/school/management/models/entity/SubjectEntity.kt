package ar.com.school.management.models.entity

import ar.com.school.management.utils.Marks
import ar.com.school.management.utils.State
import jakarta.persistence.*

@Entity
@Table(name = "subjects")
class SubjectEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,
    var state: State,
    var mark: Marks,

    @ManyToMany(mappedBy = "subjects")
    var careers: MutableList<CareerEntity>?,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "subject_student",
        joinColumns = [JoinColumn(name = "subject_id")],
        inverseJoinColumns = [JoinColumn(name = "student_id")]
    )
    var students: MutableList<StudentEntity>?,

    @ManyToMany(mappedBy = "subjects")
    var teachers: MutableList<TeacherEntity>?


)