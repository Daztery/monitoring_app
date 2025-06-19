package com.example.monitoringapp.usecase.medicalcenter

import com.example.monitoringapp.data.model.MedicalCenter
import com.example.monitoringapp.data.network.repository.MedicalCenterRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetAllMedicalCenterUseCase @Inject constructor(private val medicalCenterRepository: MedicalCenterRepository) {
    suspend operator fun invoke(): OperationResult<CollectionResponse<MedicalCenter>> {
        return medicalCenterRepository.getAllMedicalCenter()
    }
}