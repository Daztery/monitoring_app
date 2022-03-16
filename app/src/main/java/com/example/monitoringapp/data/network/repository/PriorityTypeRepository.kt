package com.example.monitoringapp.data.network.repository

import com.example.monitoringapp.data.model.Priority
import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.service.PriorityTypeService
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class PriorityTypeRepository @Inject constructor(
    private val priorityTypeService: PriorityTypeService,
) {

    suspend fun getPriority(id: Int): OperationResult<ObjectResponse<PriorityType>> {
        return priorityTypeService.getPriority(id)
    }

    suspend fun getAllPriority(): OperationResult<CollectionResponse<Priority>> {
        return priorityTypeService.getAllPriority()
    }

}