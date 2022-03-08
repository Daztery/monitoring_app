package com.example.monitoringapp.data.network.service

import com.example.monitoringapp.data.model.MedicalCenter
import com.example.monitoringapp.data.network.api.MedicalCenterApiClient
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.GenericErrorResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.DataUtil
import com.example.monitoringapp.util.OperationResult
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class MedicalCenterService @Inject constructor(private val apiClient: MedicalCenterApiClient) {

    suspend fun getMedicalCenter(id: Int): OperationResult<ObjectResponse<MedicalCenter>> {
        try {
            val response = apiClient.getMedicalCenter(id)
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

    suspend fun getAllMedicalCenter(): OperationResult<CollectionResponse<MedicalCenter>> {
        try {
            val response = apiClient.getAllMedicalCenter()
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