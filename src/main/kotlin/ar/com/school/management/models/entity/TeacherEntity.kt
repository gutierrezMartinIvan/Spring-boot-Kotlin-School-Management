package ar.com.school.management.models.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "teachers")
@SQLDelete(sql = "UPDATE teachers SET deleted = true Where id=?")
@Where(clause = "deleted=false")
class TeacherEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    var id: Long?,

    @Column(name = "social_security_number", unique = true, nullable = false)
    var socialSecurityNumber: Int,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var surname: String,

    @Column(name = "phone_number", unique = true, nullable = false)
    var phone: Int,

    @Column(unique = true, nullable = false)
    @Email
    var email: String,

    @Column(nullable = false)
    var password: String,

    @ManyToMany(mappedBy = "teachers")
    var careers: MutableList<CareerEntity>?,

    @ManyToMany
    @JoinTable(
        name = "teacher_subject",
        joinColumns = [JoinColumn(name = "teacher_id")],
        inverseJoinColumns = [JoinColumn(name = "subject_id")]
    )
    var subjects: MutableList<SubjectEntity>? = mutableListOf(),

    var deleted: Boolean = false
)