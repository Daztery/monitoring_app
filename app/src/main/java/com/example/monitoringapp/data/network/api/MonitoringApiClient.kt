package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.data.network.request.DailyReportDateRequest
import com.example.monitoringapp.data.network.request.DailyReportRequest
import com.example.monitoringapp.data.network.request.PlanRequest
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import retrofit2.Response
import retrofit2.http.*

interface MonitoringApiClient {

    @GET("monitoring/self")
    suspend fun getSelfPlans(
        @Query("active") active: Boolean
    ): Response<CollectionResponse<Plan>>

    @GET("monitoring/{id}")
    suspend fun getPlan(
        @Path("id") id: Int,
    ): Response<ObjectResponse<Plan>>

    @POST("monitoring")
    suspend fun createPlan(
        @Body planRequest: PlanRequest
    ): Response<ObjectResponse<Plan>>

    @GET("monitoring/patient/{patientId}")
    suspend fun getPatientHistory(
        @Path("patientId") patientId: Int,
        @Query("active") active: Boolean,
        @Query("self") self: Boolean
    ): Response<CollectionResponse<Plan>>

    //Daily Report
    @POST("monitoring/{planId}/daily")
    suspend fun createDailyReport(
        @Path("planId") planId: Int,
        @Body dailyReportRequest: DailyReportRequest
    ): Response<ObjectResponse<Plan>>

    @POST("monitoring/{planId}/daily/self")
    suspend fun getByDate(
        @Path("planId") planId: Int,
        @Body dailyReportDateRequest: DailyReportDateRequest
    ): Response<CollectionResponse<Plan>>


    @POST("monitoring/{planId}/daily/patient/{patientId}")
    suspend fun getFromPatient(
        @Path("planId") planId: Int,
        @Path("patientId") patientId: Int,
        @Body dailyReportDateRequest: DailyReportDateRequest
    ): Response<CollectionResponse<Plan>>

}