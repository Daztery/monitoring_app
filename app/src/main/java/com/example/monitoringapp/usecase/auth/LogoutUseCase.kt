package com.example.monitoringapp.usecase.auth

import com.example.monitoringapp.data.network.repository.AuthenticationRepository
import com.example.monitoringapp.data.network.response.LogoutResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(refreshToken: String): OperationResult<LogoutResponse> {
        return authenticationRepository.logout(refreshToken)
    }
}