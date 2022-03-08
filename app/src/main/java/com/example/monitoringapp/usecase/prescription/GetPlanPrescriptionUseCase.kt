package com.example.monitoringapp.usecase.prescription

import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.network.repository.PrescriptionRepository
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetPlanPrescriptionUseCase @Inject constructor(private val prescriptionRepository: PrescriptionRepository) {
    suspend operator fun invoke(
        planId: Int
    ): OperationResult<ObjectResponse<Prescription>> {
        return prescriptionRepository.getPlanPrescription(planId)
    }
}