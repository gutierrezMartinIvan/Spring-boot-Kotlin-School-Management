package ar.com.school.management.repository

import ar.com.school.management.models.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
    fun findBySocialSecurityNumber(socialSecurityNumber: Int): Optional<StudentEntity>
}