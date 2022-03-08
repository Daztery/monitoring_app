package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.network.request.PlanPrescriptionRequest
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import retrofit2.Response
import retrofit2.http.*

interface PrescriptionApiClient {

    @GET("monitoring/{planId}/prescription")
    suspend fun getPlanPrescription(@Path("planId") planId: Int): Response<ObjectResponse<Prescription>>

    @POST("monitoring/{planId}/prescription")
    suspend fun createPlanPrescription(
        @Path("planId") planId: Int,
        @Body planPrescriptionRequest: PlanPrescriptionRequest
    ): Response<CollectionResponse<Prescription>>

    @GET("prescription/self")
    suspend fun getSelfPrescription(
        @Query("active") active: Boolean,
        @Query("from") from: String
    ): Response<CollectionResponse<Prescription>>

    @GET("prescription/{id}")
    suspend fun getPrescription(@Path("id") id: Int): Response<ObjectResponse<Prescription>>

}