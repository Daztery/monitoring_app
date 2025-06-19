package com.example.monitoringapp.data.network.repository

import com.example.monitoringapp.data.model.RefreshToken
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.*
import com.example.monitoringapp.data.network.response.LogoutResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.response.RegisterPatientResponse
import com.example.monitoringapp.data.network.service.AuthenticationService
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authenticationService: AuthenticationService,
) {

    suspend fun loginDoctor(user: SignInRequest): OperationResult<ObjectResponse<User>> {
        return authenticationService.loginDoctor(user)
    }

    suspend fun loginPatient(user: SignInRequest): OperationResult<ObjectResponse<User>> {
        return authenticationService.loginPatient(user)
    }

    suspend fun logout(refreshToken: String): OperationResult<LogoutResponse> {
        return authenticationService.logout(refreshToken)
    }

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): OperationResult<ObjectResponse<RefreshToken>> {
        return authenticationService.refreshToken(refreshTokenRequest)
    }

    suspend fun recoverPassword(recoverPasswordRequest: RecoverPasswordRequest): OperationResult<ObjectResponse<String>> {
        return authenticationService.recoverPassword(recoverPasswordRequest)
    }

    suspend fun updatePassword(updatePasswordRequest: UpdatePasswordRequest): OperationResult<ObjectResponse<String>> {
        return authenticationService.updatePassword(updatePasswordRequest)
    }

    suspend fun registerPatient(registerPatientRequest: RegisterPatientRequest): OperationResult<ObjectResponse<RegisterPatientResponse>> {
        return authenticationService.registerPatient(registerPatientRequest)
    }

}