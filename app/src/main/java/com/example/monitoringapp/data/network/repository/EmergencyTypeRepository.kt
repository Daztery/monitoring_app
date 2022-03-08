package com.example.monitoringapp.data.network.repository

import com.example.monitoringapp.data.model.EmergencyType
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.service.EmergencyTypeService
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class EmergencyTypeRepository @Inject constructor(
    private val emergencyTypeService: EmergencyTypeService,
) {

    suspend fun getEmergency(id: Int): OperationResult<ObjectResponse<EmergencyType>> {
        return emergencyTypeService.getEmergency(id)
    }

    suspend fun getAllEmergency(): OperationResult<CollectionResponse<EmergencyType>> {
        return emergencyTypeService.getAllEmergency()
    }

}