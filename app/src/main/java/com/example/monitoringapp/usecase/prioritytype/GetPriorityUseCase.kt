package com.example.monitoringapp.usecase.prioritytype

import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.data.network.repository.PriorityTypeRepository
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetPriorityUseCase @Inject constructor(private val priorityTypeRepository: PriorityTypeRepository) {
    suspend operator fun invoke(id:Int): OperationResult<ObjectResponse<PriorityType>> {
        return priorityTypeRepository.getPriority(id)
    }
}