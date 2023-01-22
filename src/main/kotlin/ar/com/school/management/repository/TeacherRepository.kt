package ar.com.school.management.repository

import ar.com.school.management.models.entity.TeacherEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TeacherRepository : JpaRepository<TeacherEntity, Long> {
    fun findByPhone(phone: Int): Optional<TeacherEntity>
    fun findBySocialSecurityNumber(ssNumber: Int): Optional<TeacherEntity>
    fun findByEmail(email: String): Optional<TeacherEntity>
}