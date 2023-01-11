package ar.com.school.management.repository

import ar.com.school.management.models.entity.CareerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CareerRepository : JpaRepository<CareerEntity, Long> {
}