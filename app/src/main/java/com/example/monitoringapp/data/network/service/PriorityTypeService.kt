package com.example.monitoringapp.data.network.service

import com.example.monitoringapp.data.model.Priority
import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.data.network.api.PriorityTypeApiClient
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.GenericErrorResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.DataUtil
import com.example.monitoringapp.util.OperationResult
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class PriorityTypeService @Inject constructor(private val apiClient: PriorityTypeApiClient) {

    suspend fun getPriority(id: Int): OperationResult<ObjectResponse<PriorityType>> {
        try {
            val response = apiClient.getPriority(id)
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

    suspend fun getAllPriority(): OperationResult<CollectionResponse<Priority>> {
        try {
            val response = apiClient.getAllPriority()
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