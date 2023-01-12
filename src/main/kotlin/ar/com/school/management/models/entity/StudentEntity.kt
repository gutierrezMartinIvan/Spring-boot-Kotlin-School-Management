package ar.com.school.management.models.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "students")
@SQLDelete(sql = "UPDATE students SET deleted = true Where id=?")
@Where(clause = "deleted=false")
class StudentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
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

    @ManyToOne
    @JoinColumn(name = "career")
    var career: CareerEntity?,

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    var subjects: MutableList<SubjectEntity>?,

    var deleted: Boolean = false
)