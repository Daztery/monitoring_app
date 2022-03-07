package com.example.monitoringapp.data.network.repository

import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.service.UserService
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService,
) {

    suspend fun getSelf(): OperationResult<ObjectResponse<User>> {
        return userService.getSelf()
    }

    suspend fun getPatient(identification: Int): OperationResult<ObjectResponse<User>> {
        return userService.getPatient(identification)
    }

}