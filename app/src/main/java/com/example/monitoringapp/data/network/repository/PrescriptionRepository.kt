package com.example.monitoringapp.data.network.repository

import android.util.Log
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.network.request.PlanPrescriptionRequest
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.service.PrescriptionService
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class PrescriptionRepository @Inject constructor(
    private val prescriptionService: PrescriptionService,
) {

    suspend fun getPlanPrescription(
        planId: Int
    ): OperationResult<ObjectResponse<Prescription>> {
        return prescriptionService.getPlanPrescription(planId)
    }

    suspend fun createPlanPrescription(
        planId: Int,
        planPrescriptionRequest: PlanPrescriptionRequest
    ): OperationResult<ObjectResponse<Prescription>> {
        return prescriptionService.createPlanPrescription(planId, planPrescriptionRequest)
    }

    suspend fun getSelfPrescription(
        active: Boolean,
        from: String,
        to: String
    ): OperationResult<CollectionResponse<Prescription>> {
        return prescriptionService.getSelfPrescription(active, from, to)
    }

    suspend fun getPrescription(
        id: Int
    ): OperationResult<ObjectResponse<Prescription>> {
        return prescriptionService.getPrescription(id)
    }

}