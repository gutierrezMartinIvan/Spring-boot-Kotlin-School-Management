package ar.com.school.management.models.entity

import ar.com.school.management.utils.Role
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "students")
@SQLDelete(sql = "UPDATE students SET deleted = true Where id=?")
@Where(clause = "deleted=false")
class StudentEntity(
    override var id: Long?,
    override var socialSecurityNumber: Int?,
    override var name: String?,
    override var surname: String?,
    override var phone: Int?,
    override var email: String?,
    override var pw: String?,
    override var role: Role?,

    @ManyToOne
    @JoinColumn(name = "career")
    var career: CareerEntity?,

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    var subjects: MutableList<SubjectEntity>?,

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL])
    var studentSubjects: MutableList<StudentSubjectEntity>?,

    var deleted: Boolean = false

): User(id, socialSecurityNumber, name, surname, phone, email, pw, role)