package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.SignInRequest
import com.example.monitoringapp.data.network.response.ObjectResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthenticationApiClient {

    @POST("auth/signin/doctor")
    suspend fun loginDoctor(@Body user: SignInRequest): Response<ObjectResponse<User>>

    @POST("auth/signin/patient")
    suspend fun loginPatient(@Body user: SignInRequest): Response<ObjectResponse<User>>

    /*@GET("auth/extend-token")
    suspend fun refreshToken(): Response<ExtendTokenResponse>

    @GET("users/self/logout")
    suspend fun logout(
        @Query("deviceUUID") deviceUUID:String
    ): Response<AuthResponse>*/

}