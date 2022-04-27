package com.example.monitoringapp.data.network.repository

import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.TemperatureSaturation
import com.example.monitoringapp.data.network.request.DailyReportDateRequest
import com.example.monitoringapp.data.network.request.DailyReportRequest
import com.example.monitoringapp.data.network.request.PlanRequest
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.service.MonitoringService
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class MonitoringRepository @Inject constructor(
    private val monitoringService: MonitoringService,
) {

    suspend fun getSelfPlans(
        active: Boolean
    ): OperationResult<CollectionResponse<Plan>> {
        return monitoringService.getSelfPlans(active)
    }

    suspend fun getPlan(
        id: Int
    ): OperationResult<ObjectResponse<Plan>> {
        return monitoringService.getPlan(id)
    }

    suspend fun createPlan(
        planRequest: PlanRequest
    ): OperationResult<ObjectResponse<Plan>> {
        return monitoringService.createPlan(planRequest)
    }

    suspend fun getPatientHistory(
        patientId: Int,
        active: Boolean,
        self: Boolean
    ): OperationResult<CollectionResponse<Plan>> {
        return monitoringService.getPatientHistory(patientId, active, self)
    }

    //Daily Report
    suspend fun createDailyReport(
        planId: Int,
        dailyReportRequest: DailyReportRequest
    ): OperationResult<ObjectResponse<TemperatureSaturation>> {
        return monitoringService.createDailyReport(planId, dailyReportRequest)
    }

    suspend fun getByDate(
        planId: Int,
        dailyReportDateRequest: DailyReportDateRequest
    ): OperationResult<ObjectResponse<TemperatureSaturation>> {
        return monitoringService.getByDate(planId, dailyReportDateRequest)
    }

    suspend fun getFromPatient(
        planId: Int,
        patientId: Int,
        dailyReportDateRequest: DailyReportDateRequest
    ): OperationResult<ObjectResponse<TemperatureSaturation>> {
        return monitoringService.getFromPatient(planId, patientId, dailyReportDateRequest)
    }

}