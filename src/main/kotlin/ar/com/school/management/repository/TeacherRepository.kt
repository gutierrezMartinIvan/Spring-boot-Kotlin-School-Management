package ar.com.school.management.repository

import ar.com.school.management.models.entity.TeacherEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository: JpaRepository<TeacherEntity, Long> {
}