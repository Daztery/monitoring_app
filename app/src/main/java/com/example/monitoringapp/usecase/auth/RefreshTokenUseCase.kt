package com.example.monitoringapp.usecase.auth

import com.example.monitoringapp.data.model.RefreshToken
import com.example.monitoringapp.data.network.repository.AuthenticationRepository
import com.example.monitoringapp.data.network.request.RefreshTokenRequest
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(refreshTokenRequest: RefreshTokenRequest): OperationResult<ObjectResponse<RefreshToken>> {
        return authenticationRepository.refreshToken(refreshTokenRequest)
    }
}