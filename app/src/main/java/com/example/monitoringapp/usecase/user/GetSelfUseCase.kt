package com.example.monitoringapp.usecase.user

import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.repository.UserRepository
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetSelfUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): OperationResult<ObjectResponse<User>> {
        return userRepository.getSelf()
    }
}