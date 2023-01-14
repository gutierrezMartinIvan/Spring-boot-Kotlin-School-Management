package ar.com.school.management.models.entity

import ar.com.school.management.utils.Marks
import ar.com.school.management.utils.State
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "subjects")
@SQLDelete(sql = "UPDATE subjects SET deleted = true Where id=?")
@Where(clause = "deleted=false")
class SubjectEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    var id: Long?,

    @Column(nullable = false)
    var name: String,

    var state: State,
    var mark: Marks,

    @ManyToMany
    @JoinTable(
        name = "subject_student",
        joinColumns = [JoinColumn(name = "subject_id")],
        inverseJoinColumns = [JoinColumn(name = "student_id")]
    )
    var students: MutableList<StudentEntity>?,

    @ManyToMany(mappedBy = "subjects")
    var teachers: MutableList<TeacherEntity>?,

    @ManyToMany(mappedBy = "subjects")
    var careers: MutableList<CareerEntity>?,

    var deleted: Boolean = false
)