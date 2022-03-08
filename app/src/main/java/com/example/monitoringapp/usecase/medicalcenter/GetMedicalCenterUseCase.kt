package com.example.monitoringapp.usecase.medicalcenter

import com.example.monitoringapp.data.model.MedicalCenter
import com.example.monitoringapp.data.network.repository.MedicalCenterRepository
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetMedicalCenterUseCase @Inject constructor(private val medicalCenterRepository: MedicalCenterRepository) {
    suspend operator fun invoke(id:Int): OperationResult<ObjectResponse<MedicalCenter>> {
        return medicalCenterRepository.getMedicalCenter(id)
    }
}