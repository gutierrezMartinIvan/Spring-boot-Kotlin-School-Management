package ar.com.school.management.models.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "careers")
@SQLDelete(sql = "UPDATE careers SET deleted = true Where career_id=?")
@Where(clause = "deleted=false")
class CareerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    var id: Long?,

    @Column(nullable = false, unique = true)
    var name: String,

    var description: String?,

    @ManyToMany
    @JoinTable(
        name = "career_subject",
        joinColumns = [JoinColumn(name = "career_id")],
        inverseJoinColumns = [JoinColumn(name = "subject_id")]
    )
    var subjects: MutableList<SubjectEntity>?,

    @OneToMany(mappedBy = "career", cascade = [CascadeType.ALL])
    var students: MutableList<StudentEntity>?,

    var deleted: Boolean = false
)