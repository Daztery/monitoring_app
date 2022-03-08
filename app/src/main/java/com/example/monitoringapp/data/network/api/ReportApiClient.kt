package com.example.monitoringapp.data.network.api

import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.data.network.response.CollectionResponse
import retrofit2.Response
import retrofit2.http.*

interface ReportApiClient {

    @GET("report/priority")
    suspend fun getPriorityReport(
        @Query("active") active: Boolean,
        @Query("from") from: String
    ): Response<CollectionResponse<Report>>

    @GET("report/emergency")
    suspend fun getEmergencyReport(
        @Query("active") active: Boolean,
        @Query("from") from: String
    ): Response<CollectionResponse<Report>>

    @GET("report/patient/emergency/{emergencyId}")
    suspend fun getPatientsByEmergency(
        @Path("emergencyId") emergencyId: Int,
        @Query("active") active: Boolean,
        @Query("from") from: String
    ): Response<CollectionResponse<Report>>

    @GET("report/patient/priorityId/{priorityId}")
    suspend fun getPatientsByPriority(
        @Path("priorityId") priorityId: Int,
        @Query("active") active: Boolean,
        @Query("from") from: String
    ): Response<CollectionResponse<Report>>

    @GET("report/patient/status")
    suspend fun getPatientStatus(
        @Query("active") active: Boolean,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<CollectionResponse<Report>>

    @GET("report/patient/status/resume")
    suspend fun getPatientStatusResume(
        @Query("active") active: Boolean,
        @Query("from") from: String
    ): Response<CollectionResponse<Report>>

}