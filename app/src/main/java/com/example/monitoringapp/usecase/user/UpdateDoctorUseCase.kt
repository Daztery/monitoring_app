package com.example.monitoringapp.usecase.user

import com.example.monitoringapp.data.network.repository.UserRepository
import com.example.monitoringapp.data.network.request.UpdateDoctorRequest
import com.example.monitoringapp.data.network.response.UpdateResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class UpdateDoctorUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        id: Int,
        updateDoctorRequest: UpdateDoctorRequest
    ): OperationResult<UpdateResponse> {
        return userRepository.updateDoctor(id, updateDoctorRequest)
    }
}