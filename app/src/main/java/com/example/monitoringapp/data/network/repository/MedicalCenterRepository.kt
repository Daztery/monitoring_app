package com.example.monitoringapp.data.network.repository

import com.example.monitoringapp.data.model.MedicalCenter
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.data.network.service.MedicalCenterService
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class MedicalCenterRepository @Inject constructor(
    private val MedicalCenterService: MedicalCenterService,
) {

    suspend fun getMedicalCenter(id: Int): OperationResult<ObjectResponse<MedicalCenter>> {
        return MedicalCenterService.getMedicalCenter(id)
    }

    suspend fun getAllMedicalCenter(): OperationResult<CollectionResponse<MedicalCenter>> {
        return MedicalCenterService.getAllMedicalCenter()
    }

}