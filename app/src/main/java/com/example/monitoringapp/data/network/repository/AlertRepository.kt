package com.example.monitoringapp.data.network.repository

import com.example.monitoringapp.data.model.*
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.service.AlertService
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class AlertRepository @Inject constructor(
    private val alertService: AlertService,
) {

    suspend fun getSelfAlerts(): OperationResult<CollectionResponse<Alert>> {
        return alertService.getSelfAlerts()
    }

    suspend fun getAlertsFromReport(
        reportId: Int
    ): OperationResult<CollectionResponse<Alert>> {
        return alertService.getAlertsFromReport(reportId)
    }

    suspend fun getAlertsFromMonitoringPlan(
        planId: Int
    ): OperationResult<CollectionResponse<Alert>> {
        return alertService.getAlertsFromMonitoringPlan(planId)
    }

    suspend fun getAlertsFromPatient(
        patientId: Int
    ): OperationResult<CollectionResponse<Alert>> {
        return alertService.getAlertsFromPatient(patientId)
    }

    suspend fun getAllAlerts(): OperationResult<CollectionResponse<Alert>> {
        return alertService.getAllAlerts()
    }

}