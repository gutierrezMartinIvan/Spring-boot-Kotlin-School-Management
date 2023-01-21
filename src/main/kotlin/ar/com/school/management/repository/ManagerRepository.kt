package ar.com.school.management.repository

import ar.com.school.management.models.entity.ManagerEntity
import ar.com.school.management.utils.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import kotlin.collections.List

interface ManagerRepository: JpaRepository<ManagerEntity, Long> {
    fun findBySocialSecurityNumber(socialSecurityNumber: Int): Optional<ManagerEntity>
    fun findByEmail(email: String): Optional<ManagerEntity>
    fun findAllByRole(role: Role): List<ManagerEntity>
}