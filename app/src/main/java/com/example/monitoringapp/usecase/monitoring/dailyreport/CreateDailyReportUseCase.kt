package com.example.monitoringapp.usecase.monitoring.dailyreport

import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.network.repository.MonitoringRepository
import com.example.monitoringapp.data.network.request.DailyReportRequest
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class CreateDailyReportUseCase @Inject constructor(private val monitoringRepository: MonitoringRepository) {
    suspend operator fun invoke(
        planId: Int,
        dailyReportRequest: DailyReportRequest
    ): OperationResult<ObjectResponse<Plan>> {
        return monitoringRepository.createDailyReport(planId, dailyReportRequest)
    }
}