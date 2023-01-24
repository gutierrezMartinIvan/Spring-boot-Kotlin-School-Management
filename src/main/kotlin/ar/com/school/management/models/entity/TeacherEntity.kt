package ar.com.school.management.models.entity

import ar.com.school.management.utils.Role
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "teachers")
@SQLDelete(sql = "UPDATE teachers SET deleted = true Where id=?")
@Where(clause = "deleted=false")
class TeacherEntity(
    override var id: Long?,
    override var socialSecurityNumber: Int?,
    override var name: String?,
    override var surname: String?,
    override var phone: Int?,
    override var email: String?,
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