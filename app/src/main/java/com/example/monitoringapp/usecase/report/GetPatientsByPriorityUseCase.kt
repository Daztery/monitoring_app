package com.example.monitoringapp.usecase.report

import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.data.network.repository.ReportRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetPatientsByPriorityUseCase @Inject constructor(private val reportRepository: ReportRepository) {
    suspend operator fun invoke(
        priorityId: Int,
        active: Boolean,
        from: String
    ): OperationResult<CollectionResponse<PriorityType>> {
        return reportRepository.getPatientsByPriority(priorityId, active, from)
    }
}