package com.example.monitoringapp.usecase.auth

import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.repository.AuthenticationRepository
import com.example.monitoringapp.data.network.request.SignInRequest
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class LoginPatientUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(user: SignInRequest): OperationResult<ObjectResponse<User>> {
        return authenticationRepository.loginPatient(user)
    }
}