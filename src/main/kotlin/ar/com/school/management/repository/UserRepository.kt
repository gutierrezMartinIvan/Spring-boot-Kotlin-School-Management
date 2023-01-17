package ar.com.school.management.repository

import ar.com.school.management.models.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findBySocialSecurityNumber(socialSecurityNumber: Int): Optional<UserEntity>
    fun findByEmail(email: String): Optional<UserEntity>
}