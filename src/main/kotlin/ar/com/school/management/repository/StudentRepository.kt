package ar.com.school.management.repository

import ar.com.school.management.models.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StudentRepository: JpaRepository<StudentEntity, Long> {
    fun findBySocialSecurityNumber(socialSecurityNumber: Int): Optional<StudentEntity>
}