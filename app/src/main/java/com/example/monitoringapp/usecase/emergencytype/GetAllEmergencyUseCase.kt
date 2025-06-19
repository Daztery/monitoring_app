package com.example.monitoringapp.usecase.emergencytype

import com.example.monitoringapp.data.model.Emergency
import com.example.monitoringapp.data.network.repository.EmergencyTypeRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetAllEmergencyUseCase @Inject constructor(private val emergencyTypeRepository: EmergencyTypeRepository) {
    suspend operator fun invoke(): OperationResult<CollectionResponse<Emergency>> {
        return emergencyTypeRepository.getAllEmergency()
    }
}