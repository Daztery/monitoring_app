package com.example.monitoringapp.usecase.emergencytype

import com.example.monitoringapp.data.model.EmergencyType
import com.example.monitoringapp.data.network.repository.EmergencyTypeRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetAllEmergencyUseCase @Inject constructor(private val emergencyTypeRepository: EmergencyTypeRepository) {
    suspend operator fun invoke(): OperationResult<CollectionResponse<EmergencyType>> {
        return emergencyTypeRepository.getAllEmergency()
    }
}