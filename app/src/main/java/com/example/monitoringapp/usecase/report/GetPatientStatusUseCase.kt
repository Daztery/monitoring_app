package com.example.monitoringapp.usecase.report

import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.data.model.Status
import com.example.monitoringapp.data.network.repository.ReportRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetPatientStatusUseCase @Inject constructor(private val reportRepository: ReportRepository) {
    suspend operator fun invoke(
        active: Boolean,
        from: String,
        to: String
    ): OperationResult<CollectionResponse<Status>> {
        return reportRepository.getPatientStatus(active, from, to)
    }
}