package com.example.monitoringapp.usecase.user

import com.example.monitoringapp.data.network.repository.UserRepository
import com.example.monitoringapp.data.network.request.UpdatePatientRequest
import com.example.monitoringapp.data.network.response.UpdateResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class UpdatePatientUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(updatePatientRequest: UpdatePatientRequest): OperationResult<UpdateResponse> {
        return userRepository.updatePatient(updatePatientRequest)
    }
}