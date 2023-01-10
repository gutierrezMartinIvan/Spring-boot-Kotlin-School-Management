package ar.com.school.management.models.entity

import jakarta.persistence.*

@Entity
@Table(name = "students")
class StudentEntity(@Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    @Column(name = "student_id")
                    var id: Long? = null,

                    @Column(name = "social_security_number")
                    val socialSecurityNumber: Int,
                    var name: String,
                    var surname: String,
                    var phone: Int,
                    var email: String,

                    @ManyToOne(fetch = FetchType.EAGER)
                    @JoinColumn(name = "career_id")
                    var careerId: CareerEntity,

                    @ManyToMany(mappedBy = "students")
                    var subjects: List<SubjectEntity>? = mutableListOf()
) {

}