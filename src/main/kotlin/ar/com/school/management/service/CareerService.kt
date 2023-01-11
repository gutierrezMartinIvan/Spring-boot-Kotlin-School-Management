package ar.com.school.management.service

import ar.com.school.management.models.request.CareerRequest
import ar.com.school.management.models.response.CareerResponse

interface CareerService {
    fun save(request: CareerRequest): CareerResponse
}