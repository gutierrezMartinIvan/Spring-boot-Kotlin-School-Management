package ar.com.school.management.models.entity

import ar.com.school.management.utils.Role
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "managers")
@SQLDelete(sql = "UPDATE managers SET deleted = true Where id=?")
@Where(clause = "deleted=false")
class ManagerEntity(
    override var id: Long?,
    override var socialSecurityNumber: Int?,
    override var name: String?,
    override var surname: String?,
    override var phone: Int? = null,
    override var email: String?,
    override var pw: String?,
    override var role: Role?,

    var deleted: Boolean = false

): User(id, socialSecurityNumber, name, surname, phone, email, pw, role)