package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.*
import com.example.monitoringapp.data.network.response.CollectionResponse
import retrofit2.Response
import retrofit2.http.*

interface AlertApiClient {

    @GET("alert/self")
    suspend fun getSelfAlerts(): Response<CollectionResponse<Alert>>

    @GET("alert/report/{reportId}")
    suspend fun getAlertsFromReport(
        @Path("reportId") reportId: Int
    ): Response<CollectionResponse<Alert>>

    @GET("alert/plan/{planId}")
    suspend fun getAlertsFromMonitoringPlan(
        @Path("planId") planId: Int
    ): Response<CollectionResponse<Alert>>

    @GET("alert/patient/{patientId}")
    suspend fun getAlertsFromPatient(
        @Path("patientId") patientId: Int
    ): Response<CollectionResponse<Alert>>

    @GET("alert")
    suspend fun getAllAlerts(): Response<CollectionResponse<Alert>>

}