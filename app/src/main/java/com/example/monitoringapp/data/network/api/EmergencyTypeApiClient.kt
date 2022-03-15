package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.Emergency
import com.example.monitoringapp.data.model.EmergencyType
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import retrofit2.Response
import retrofit2.http.*

interface EmergencyTypeApiClient {

    @GET("emergency/{id}")
    suspend fun getEmergency(@Path("id") id: Int): Response<ObjectResponse<EmergencyType>>

    @GET("emergency")
    suspend fun getAllEmergency(): Response<CollectionResponse<Emergency>>

    /*@POST("emergency")
    suspend fun createEmergency(@Body emergencyTypeRequest: EmergencyTypeRequest): Response<UpdateResponse>

    @PUT("emergency/{id}")
    suspend fun updateEmergency(@Path("id") id: Int): Response<UpdateResponse>

    @DELETE("emergency/{id}")
    suspend fun deleteEmergency(@Path("id") id: Int): Response<UpdateResponse>*/

}