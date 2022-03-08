package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.MedicalCenter
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import retrofit2.Response
import retrofit2.http.*

interface MedicalCenterApiClient {

    @GET("medicalCenter/{id}")
    suspend fun getMedicalCenter(@Path("id") id: Int): Response<ObjectResponse<MedicalCenter>>

    @GET("medicalCenter")
    suspend fun getAllMedicalCenter(): Response<CollectionResponse<MedicalCenter>>

    /*@POST("emergency")
    suspend fun createEmergency(@Body emergencyTypeRequest: EmergencyTypeRequest): Response<UpdateResponse>

    @PUT("emergency/{id}")
    suspend fun updateEmergency(@Path("id") id: Int): Response<UpdateResponse>

    @DELETE("emergency/{id}")
    suspend fun deleteEmergency(@Path("id") id: Int): Response<UpdateResponse>*/

}