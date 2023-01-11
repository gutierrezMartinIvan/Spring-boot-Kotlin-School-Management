package ar.com.school.management.service.impl

import ar.com.school.management.exception.CareerRegisteredException
import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.response.CareerResponse
import ar.com.school.management.repository.CareerRepository
import ar.com.school.management.service.CareerService
import ar.com.school.management.utils.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CareerServiceImpl: CareerService {

    @Autowired
    private lateinit var repository: CareerRepository

    @Autowired
    private lateinit var mapper: Mapper

    override fun save(request: CareerRequest): CareerResponse {
        var entity = request.name?.let { repository.findByName(it) }
        if (entity?.isPresent == true)
            throw CareerRegisteredException("The career is already registered!")

        var entitySave = mapper.careerDto2Entity(request)
        return mapper.careerEntity2Dto(repository.save(entitySave))
    }
}