package ar.com.school.management.repository

import ar.com.school.management.models.entity.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository: JpaRepository<SubjectEntity, Long> {
}