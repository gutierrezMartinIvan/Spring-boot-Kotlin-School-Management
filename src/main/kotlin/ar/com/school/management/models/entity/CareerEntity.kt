package ar.com.school.management.models.entity

import jakarta.persistence.*

@Entity
@Table(name = "careers")
class CareerEntity(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   @Column(name = "career_id")
                   var id: Long? = null,

                   @Column(nullable = false)
                   var name: String,

                   @ManyToMany
                   @JoinTable(
                       name = "career_subject",
                       joinColumns = [JoinColumn(name = "career_id")],
                       inverseJoinColumns = [JoinColumn(name = "subject_id")])
                   var subjects: List<SubjectEntity>?,

                   @OneToMany(mappedBy = "careerId", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
                   var students: List<StudentEntity>? = mutableListOf(),

                   @ManyToMany
                   @JoinTable(
                       name = "career_teacher",
                       joinColumns = [JoinColumn(name = "career_id")],
                       inverseJoinColumns = [JoinColumn(name = "teacher_id")]
                   )
                   var teachers: List<TeacherEntity> = mutableListOf(),
)