package ar.com.school.management.repository

import ar.com.school.management.models.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository: JpaRepository<StudentEntity, Long> {
}