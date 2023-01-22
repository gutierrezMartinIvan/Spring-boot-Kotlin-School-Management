package ar.com.school.management.repository

import ar.com.school.management.models.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
    fun findByPhone(phone: Int): Optional<StudentEntity>
    fun findBySocialSecurityNumber(ssNumber: Int): Optional<StudentEntity>
    fun findByEmail(email: String): Optional<StudentEntity>
}