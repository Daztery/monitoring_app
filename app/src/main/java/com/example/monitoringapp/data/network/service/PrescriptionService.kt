package com.example.monitoringapp.data.network.service

import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.network.api.PrescriptionApiClient
import com.example.monitoringapp.data.network.request.PlanPrescriptionRequest
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.GenericErrorResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.DataUtil
import com.example.monitoringapp.util.OperationResult
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class PrescriptionService @Inject constructor(private val apiClient: PrescriptionApiClient) {

    suspend fun getPlanPrescription(planId: Int): OperationResult<ObjectResponse<Prescription>> {
        try {
            val response = apiClient.getPlanPrescription(planId)
            response.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()
                    OperationResult.Success(data)
                } else {
                    val type = object : TypeToken<GenericErrorResponse>() {}.type
                    val errorData = response.errorBody()!!.charStream()
                    val errorResponse: GenericErrorResponse? = DataUtil.getFromJson(errorData, type)
                    OperationResult.Error(errorResponse?.error?.message ?: Constants.DEFAULT_ERROR)
                }
            }
        } catch (e: Exception) {
            return OperationResult.Error(e.message ?: Constants.DEFAULT_ERROR)
        }
    }

    suspend fun createPlanPrescription(
        planId: Int,
        planPrescriptionRequest: PlanPrescriptionRequest
    ): OperationResult<CollectionResponse<Prescription>> {
        try {
            val response = apiClient.createPlanPrescription(planId, planPrescriptionRequest)
            response.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()
                    OperationResult.Success(data)
                } else {
                    val type = object : TypeToken<GenericErrorResponse>() {}.type
                    val errorData = response.errorBody()!!.charStream()
                    val errorResponse: GenericErrorResponse? = DataUtil.getFromJson(errorData, type)
                    OperationResult.Error(errorResponse?.error?.message ?: Constants.DEFAULT_ERROR)
                }
            }
        } catch (e: Exception) {
            return OperationResult.Error(e.message ?: Constants.DEFAULT_ERROR)
        }
    }

    suspend fun getSelfPrescription(
        active: Boolean,
        from: String,
        to: String
    ): OperationResult<CollectionResponse<Prescription>> {
        try {
            val response = apiClient.getSelfPrescription(active, from, to)
            response.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()
                    OperationResult.Success(data)
                } else {
                    val type = object : TypeToken<GenericErrorResponse>() {}.type
                    val errorData = response.errorBody()!!.charStream()
                    val errorResponse: GenericErrorResponse? = DataUtil.getFromJson(errorData, type)
                    OperationResult.Error(errorResponse?.error?.message ?: Constants.DEFAULT_ERROR)
                }
            }
        } catch (e: Exception) {
            return OperationResult.Error(e.message ?: Constants.DEFAULT_ERROR)
        }
    }

    suspend fun getPrescription(id: Int): OperationResult<ObjectResponse<Prescription>> {
        try {
            val response = apiClient.getPrescription(id)
            response.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()
                    OperationResult.Success(data)
                } else {
                    val type = object : TypeToken<GenericErrorResponse>() {}.type
                    val errorData = response.errorBody()!!.charStream()
                    val errorResponse: GenericErrorResponse? = DataUtil.getFromJson(errorData, type)
                    OperationResult.Error(errorResponse?.error?.message ?: Constants.DEFAULT_ERROR)
                }
            }
        } catch (e: Exception) {
            return OperationResult.Error(e.message ?: Constants.DEFAULT_ERROR)
        }
    }

}