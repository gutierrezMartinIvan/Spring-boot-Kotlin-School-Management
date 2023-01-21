package ar.com.school.management.service.impl

import ar.com.school.management.exception.NotFoundException
import ar.com.school.management.models.entity.SubjectEntity
import ar.com.school.management.models.request.SubjectRequest
import ar.com.school.management.models.response.SubjectResponse
import ar.com.school.management.repository.SubjectRepository
import ar.com.school.management.service.SubjectService
import ar.com.school.management.utils.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubjectServiceImpl: SubjectService {

    @Autowired
    private lateinit var repository: SubjectRepository

    @Autowired
    private lateinit var mapper: Mapper
    override fun save(request: SubjectRequest): SubjectResponse {
        //TODO("There's to have to implement exceptions")
        var entitySave = mapper.map(request, SubjectEntity::class.java)
        return mapper.map(repository.save(entitySave), SubjectResponse::class.java)
    }

    override fun getSubjectById(id: Long): SubjectResponse =
        mapper.map(repository.findById(id)
            .orElseThrow { NotFoundException("The id: $id does not belong to any subject registered") }, SubjectResponse::class.java)
}