package com.example.monitoringapp.usecase.report

import com.example.monitoringapp.data.model.ReportStatus
import com.example.monitoringapp.data.network.repository.ReportRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetPatientStatusResumeUseCase @Inject constructor(private val reportRepository: ReportRepository) {
    suspend operator fun invoke(
        active: Boolean,
        from: String
    ): OperationResult<CollectionResponse<ReportStatus>> {
        return reportRepository.getPatientStatusResume(active, from)
    }
}