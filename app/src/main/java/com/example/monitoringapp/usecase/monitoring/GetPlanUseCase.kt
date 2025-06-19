package com.example.monitoringapp.usecase.monitoring

import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.network.repository.MonitoringRepository
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetPlanUseCase @Inject constructor(private val monitoringRepository: MonitoringRepository) {
    suspend operator fun invoke(
        id: Int
    ): OperationResult<ObjectResponse<Plan>> {
        return monitoringRepository.getPlan(id)
    }
}