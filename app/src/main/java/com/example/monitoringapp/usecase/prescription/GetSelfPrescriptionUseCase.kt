package com.example.monitoringapp.usecase.prescription

import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.network.repository.PrescriptionRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetSelfPrescriptionUseCase @Inject constructor(private val prescriptionRepository: PrescriptionRepository) {
    suspend operator fun invoke(
        active: Boolean,
        from: String
    ): OperationResult<CollectionResponse<Prescription>> {
        return prescriptionRepository.getSelfPrescription(active, from)
    }
}