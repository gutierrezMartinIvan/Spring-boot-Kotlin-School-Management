package ar.com.school.management.models.entity

import ar.com.school.management.utils.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "students")
@SQLDelete(sql = "UPDATE students SET deleted = true Where student_id=?")
@Where(clause = "deleted=false")
class StudentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    override var id: Long?,

    @Column(name = "social_security_number", unique = true, nullable = false)
    override var socialSecurityNumber: Int?,

    @Column(nullable = false)
    override var name: String?,

    @Column(nullable = false)
    override var surname: String?,

    @Column(name = "phone_number", unique = true, nullable = false)
    override var phone: Int?,

    @Column(unique = true, nullable = false)
    @Email
    override var email: String?,

    @Column(name = "password", nullable = false)
    override var pw: String?,

    @ManyToOne
    @JoinColumn(name = "career")
    var career: CareerEntity?,

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    var subjects: MutableList<SubjectEntity>?,

    @Enumerated(EnumType.STRING)
    override var role: Role?,

    var deleted: Boolean = false

): User(id, socialSecurityNumber, name, surname, phone, email, pw, role)