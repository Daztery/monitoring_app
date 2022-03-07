package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.UpdateDoctorRequest
import com.example.monitoringapp.data.network.request.UpdatePatientRequest
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.response.UpdateResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApiClient {

    @GET("user/self")
    suspend fun getSelf(): Response<ObjectResponse<User>>

    @GET("user/patient/{identification}")
    suspend fun getPatient(@Path("identification") identification: Int): Response<ObjectResponse<User>>

    @POST("user/{id}")
    suspend fun updatePatient(@Body updatePatientRequest: UpdatePatientRequest): Response<UpdateResponse>

    @POST("user/{id}")
    suspend fun updateDoctor(@Body updateDoctorRequest: UpdateDoctorRequest): Response<UpdateResponse>

}