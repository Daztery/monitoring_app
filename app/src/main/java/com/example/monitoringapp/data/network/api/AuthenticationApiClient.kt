package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.RefreshToken
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.RecoverPasswordRequest
import com.example.monitoringapp.data.network.request.RefreshTokenRequest
import com.example.monitoringapp.data.network.request.SignInRequest
import com.example.monitoringapp.data.network.request.UpdatePasswordRequest
import com.example.monitoringapp.data.network.response.LogoutResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthenticationApiClient {

    @POST("auth/signin/doctor")
    suspend fun loginDoctor(@Body user: SignInRequest): Response<ObjectResponse<User>>

    @POST("auth/signin/patient")
    suspend fun loginPatient(@Body user: SignInRequest): Response<ObjectResponse<User>>

    @DELETE("/auth/signout/{refreshToken}")
    suspend fun logout(@Path("refreshToken") refreshToken: String): Response<LogoutResponse>

    @POST("auth/token/refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<ObjectResponse<RefreshToken>>

    @POST("auth/password/forgot")
    suspend fun recoverPassword(@Body recoverPasswordRequest: RecoverPasswordRequest): Response<ObjectResponse<String>>

    @PUT("auth/password/update")
    suspend fun updatePassword(@Body updatePasswordRequest: UpdatePasswordRequest): Response<ObjectResponse<String>>

}