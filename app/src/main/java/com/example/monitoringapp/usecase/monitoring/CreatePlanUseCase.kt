package com.example.monitoringapp.usecase.monitoring

import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.network.repository.MonitoringRepository
import com.example.monitoringapp.data.network.request.PlanRequest
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class CreatePlanUseCase @Inject constructor(private val monitoringRepository: MonitoringRepository) {
    suspend operator fun invoke(
        planRequest: PlanRequest
    ): OperationResult<ObjectResponse<Plan>> {
        return monitoringRepository.createPlan(planRequest)
    }
}