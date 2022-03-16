package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.Priority
import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import retrofit2.Response
import retrofit2.http.*

interface PriorityTypeApiClient {

    @GET("priority/{id}")
    suspend fun getPriority(@Path("id") id: Int): Response<ObjectResponse<PriorityType>>

    @GET("priority")
    suspend fun getAllPriority(): Response<CollectionResponse<Priority>>

    /*@POST("emergency")
    suspend fun createEmergency(@Body emergencyTypeRequest: EmergencyTypeRequest): Response<UpdateResponse>

    @PUT("emergency/{id}")
    suspend fun updateEmergency(@Path("id") id: Int): Response<UpdateResponse>

    @DELETE("emergency/{id}")
    suspend fun deleteEmergency(@Path("id") id: Int): Response<UpdateResponse>*/

}