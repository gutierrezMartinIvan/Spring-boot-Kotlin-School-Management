package ar.com.school.management.models.entity

import ar.com.school.management.utils.Mark
import ar.com.school.management.utils.Status
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "student_subject_status")
@SQLDelete(sql = "UPDATE student_subject_status SET deleted = true Where id=?")
@Where(clause = "deleted=false")
class StudentSubjectEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne
    @JoinColumn(name = "student_id")
    var student: StudentEntity?,

    @ManyToOne
    @JoinColumn(name = "subject_id")
    var subject: SubjectEntity?,

    @Enumerated
    var mark: Mark?,

    @Enumerated
    var status: Status?,

    var deleted: Boolean? = false
)