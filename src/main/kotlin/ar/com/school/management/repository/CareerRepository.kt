package ar.com.school.management.repository

import ar.com.school.management.models.entity.CareerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CareerRepository : JpaRepository<CareerEntity, Long> {
    fun findByName(name: String): Optional<CareerEntity>
}