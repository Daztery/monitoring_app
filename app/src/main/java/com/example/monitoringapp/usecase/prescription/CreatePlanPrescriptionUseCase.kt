package com.example.monitoringapp.usecase.prescription

import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.network.repository.PrescriptionRepository
import com.example.monitoringapp.data.network.request.PlanPrescriptionRequest
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class CreatePlanPrescriptionUseCase @Inject constructor(private val prescriptionRepository: PrescriptionRepository) {
    suspend operator fun invoke(
        planId: Int,
        planPrescriptionRequest: PlanPrescriptionRequest
    ): OperationResult<ObjectResponse<Prescription>> {
        return prescriptionRepository.createPlanPrescription(planId, planPrescriptionRequest)
    }
}