package ar.com.school.management.models.entity

import ar.com.school.management.utils.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "managers")
@SQLDelete(sql = "UPDATE managers SET deleted = true Where manager_id=?")
@Where(clause = "deleted=false")
class ManagerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    override var id: Long?,

    @Column(name = "social_security_number", unique = true, nullable = false)
    override var socialSecurityNumber: Int?,

    @Column(nullable = false)
    override var name: String?,

    @Column(nullable = false)
    override var surname: String?,

    @Column(name = "phone_number", unique = true, nullable = false)
    override var phone: Int? = null,

    @Column(unique = true, nullable = false)
    @Email
    override var email: String?,

    @Column(name = "password", nullable = false)
    override var pw: String?,

    @Enumerated(EnumType.STRING)
    override var role: Role?,

    var deleted: Boolean = false

): User(id, socialSecurityNumber, name, surname, phone, email, pw, role)