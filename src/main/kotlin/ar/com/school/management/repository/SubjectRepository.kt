package ar.com.school.management.repository

import ar.com.school.management.models.entity.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SubjectRepository : JpaRepository<SubjectEntity, Long>