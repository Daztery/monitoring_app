package com.example.monitoringapp.usecase.emergencytype

import com.example.monitoringapp.data.model.EmergencyType
import com.example.monitoringapp.data.network.repository.EmergencyTypeRepository
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetEmergencyUseCase @Inject constructor(private val emergencyTypeRepository: EmergencyTypeRepository) {
    suspend operator fun invoke(id:Int): OperationResult<ObjectResponse<EmergencyType>> {
        return emergencyTypeRepository.getEmergency(id)
    }
}