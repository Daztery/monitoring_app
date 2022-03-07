package com.example.monitoringapp.data.network.service

import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.api.AuthenticationApiClient
import com.example.monitoringapp.data.network.request.SignInRequest
import com.example.monitoringapp.data.network.response.GenericErrorResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.DataUtil
import com.example.monitoringapp.util.OperationResult
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class AuthenticationService @Inject constructor(private val apiClient: AuthenticationApiClient) {

    suspend fun loginDoctor(user: SignInRequest): OperationResult<ObjectResponse<User>> {
        try {
            val response = apiClient.loginDoctor(user)
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

    suspend fun loginPatient(user: SignInRequest): OperationResult<ObjectResponse<User>> {
        try {
            val response = apiClient.loginPatient(user)
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


    /*suspend fun forgotPassword(user: User): OperationResult<Boolean> {
        try {
            val response = apiClient.forgotPassword(user)
            response.let {
                return if (it.isSuccessful) {
                    OperationResult.Success(true)
                } else {
                    OperationResult.Success(false)
                }
            }
        } catch (e: Exception) {
            return OperationResult.Error(e.message ?: Constants.DEFAULT_ERROR)
        }
    }

    suspend fun extendToken(): OperationResult<ExtendTokenResponse> {
        try {
            val response = apiClient.extendToken()
            response.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()
                    OperationResult.Success(data)
                } else {
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorData = response.errorBody()!!.charStream()
                    val errorResponse: ErrorResponse? = DataUtil.getFromJson(errorData, type)
                    OperationResult.Error(errorResponse?.message ?: Constants.DEFAULT_ERROR)
                }
            }
        } catch (e: Exception) {
            return OperationResult.Error(e.message ?: Constants.DEFAULT_ERROR)
        }
    }

    suspend fun logout(deviceUUID: String): OperationResult<Boolean> {
        try {
            val response = apiClient.logout(deviceUUID)
            response.let {
                return if (response.isSuccessful) {
                    OperationResult.Success(true)
                } else {
                    OperationResult.Success(false)
                }
            }
        } catch (e: Exception) {
            return OperationResult.Error(e.message ?: Constants.DEFAULT_ERROR)
        }
    }*/

}