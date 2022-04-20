package com.example.monitoringapp.usecase.alert

import com.example.monitoringapp.data.model.Alert
import com.example.monitoringapp.data.network.repository.AlertRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetAlertsFromPatientUseCase @Inject constructor(private val alertRepository: AlertRepository) {
    suspend operator fun invoke(patientId: Int): OperationResult<CollectionResponse<Alert>> {
        return alertRepository.getAlertsFromPatient(patientId)
    }
}