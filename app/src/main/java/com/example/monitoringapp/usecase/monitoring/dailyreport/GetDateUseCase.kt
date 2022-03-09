package com.example.monitoringapp.usecase.monitoring.dailyreport

import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.network.repository.MonitoringRepository
import com.example.monitoringapp.data.network.request.DailyReportDateRequest
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetDateUseCase @Inject constructor(private val monitoringRepository: MonitoringRepository) {
    suspend operator fun invoke(
        planId: Int,
        dailyReportDateRequest: DailyReportDateRequest
    ): OperationResult<CollectionResponse<Plan>> {
        return monitoringRepository.getByDate(planId, dailyReportDateRequest)
    }
}