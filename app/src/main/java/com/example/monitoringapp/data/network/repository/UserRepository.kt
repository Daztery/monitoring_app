package com.example.monitoringapp.data.network.repository

import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.UpdateDoctorRequest
import com.example.monitoringapp.data.network.request.UpdatePatientRequest
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.response.UpdateResponse
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

    suspend fun updatePatient(
        id: Int,
        updatePatientRequest: UpdatePatientRequest
    ): OperationResult<UpdateResponse> {
        return userService.updatePatient(id, updatePatientRequest)
    }

    suspend fun updateDoctor(
        id: Int,
        updateDoctorRequest: UpdateDoctorRequest
    ): OperationResult<UpdateResponse> {
        return userService.updateDoctor(id, updateDoctorRequest)
    }

}