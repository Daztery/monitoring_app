package com.example.monitoringapp.usecase.prioritytype

import com.example.monitoringapp.data.model.Priority
import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.data.network.repository.PriorityTypeRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetAllPriorityUseCase @Inject constructor(private val priorityTypeRepository: PriorityTypeRepository) {
    suspend operator fun invoke(): OperationResult<CollectionResponse<Priority>> {
        return priorityTypeRepository.getAllPriority()
    }
}