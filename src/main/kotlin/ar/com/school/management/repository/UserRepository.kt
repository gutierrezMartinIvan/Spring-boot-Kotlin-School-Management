package ar.com.school.management.repository

import ar.com.school.management.models.entity.UserEntity
import ar.com.school.management.utils.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.List

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findBySocialSecurityNumber(socialSecurityNumber: Int): Optional<UserEntity>
    fun findByEmail(email: String): Optional<UserEntity>
    fun findAllByRole(role: Role): List<UserEntity>
}