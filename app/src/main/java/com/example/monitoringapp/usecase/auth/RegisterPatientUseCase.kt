package com.example.monitoringapp.usecase.auth

import com.example.monitoringapp.data.network.repository.AuthenticationRepository
import com.example.monitoringapp.data.network.request.RegisterPatientRequest
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.response.RegisterPatientResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class RegisterPatientUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(registerPatientRequest: RegisterPatientRequest): OperationResult<ObjectResponse<RegisterPatientResponse>> {
        return authenticationRepository.registerPatient(registerPatientRequest)
    }
}