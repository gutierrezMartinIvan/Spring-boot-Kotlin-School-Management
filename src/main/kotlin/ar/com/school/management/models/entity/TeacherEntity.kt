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
@Table(name = "teachers")
@SQLDelete(sql = "UPDATE teachers SET deleted = true Where teacher_id=?")
@Where(clause = "deleted=false")
class TeacherEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
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

    @ManyToMany
    @JoinTable(
        name = "teacher_subject",
        joinColumns = [JoinColumn(name = "teacher_id")],
        inverseJoinColumns = [JoinColumn(name = "subject_id")]
    )
    var subjects: MutableList<SubjectEntity>?,

    @Enumerated(EnumType.STRING)
    override var role: Role?,

    var deleted: Boolean = false

): User(id, socialSecurityNumber, name, surname, phone, email, pw, role)