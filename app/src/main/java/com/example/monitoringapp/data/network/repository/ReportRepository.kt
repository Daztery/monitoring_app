package com.example.monitoringapp.data.network.repository

import com.example.monitoringapp.data.model.*
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.service.ReportService
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val reportService: ReportService,
) {

    suspend fun getPriorityReport(
        active: Boolean,
        from: String
    ): OperationResult<CollectionResponse<Report>> {
        return reportService.getPriorityReport(active, from)
    }

    suspend fun getEmergencyReport(
        active: Boolean,
        from: String
    ): OperationResult<CollectionResponse<Report>> {
        return reportService.getEmergencyReport(active, from)
    }

    suspend fun getPatientsByEmergency(
        emergencyId: Int,
        active: Boolean,
        from: String
    ): OperationResult<CollectionResponse<EmergencyType>> {
        return reportService.getPatientsByEmergency(emergencyId, active, from)
    }

    suspend fun getPatientsByPriority(
        priorityId: Int,
        active: Boolean,
        from: String
    ): OperationResult<CollectionResponse<PriorityType>> {
        return reportService.getPatientsByPriority(priorityId, active, from)
    }

    suspend fun getPatientStatus(
        active: Boolean,
        from: String,
        to: String
    ): OperationResult<CollectionResponse<Status>> {
        return reportService.getPatientStatus(active, from, to)
    }

    suspend fun getPatientStatusResume(
        active: Boolean,
        from: String
    ): OperationResult<CollectionResponse<ReportStatus>> {
        return reportService.getPatientStatusResume(active, from)
    }

}