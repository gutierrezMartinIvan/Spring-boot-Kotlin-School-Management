package ar.com.school.management.models.entity

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
    @Column(name = "subject_id")
    var id: Long?,

    @Column(nullable = false)
    var name: String?,

    @ManyToMany
    @JoinTable(
        name = "subject_student",
        joinColumns = [JoinColumn(name = "subject_id")],
        inverseJoinColumns = [JoinColumn(name = "student_id")]
    )
    var students: MutableList<StudentEntity>?,

    @OneToMany(mappedBy = "subject", cascade = [CascadeType.ALL])
    var studentSubjects: MutableList<StudentSubjectEntity>?,

    @ManyToMany(mappedBy = "subjects")
    var teachers: MutableList<TeacherEntity>?,

    @ManyToMany(mappedBy = "subjects")
    var careers: MutableList<CareerEntity>?,

    var deleted: Boolean? = false
) {
    constructor(): this(null, null, null, null, null, null, false)
}