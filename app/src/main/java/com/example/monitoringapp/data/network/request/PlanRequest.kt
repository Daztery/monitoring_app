package com.example.monitoringapp.data.network.request

data class PlanRequest(
    var patientId: Int? = 0,
    var code: Int? = 0,
    var endDate: Long? = 0,
    var startDate: Long? = 0,
    var emergencyTypeId: Int? = 0,
    var priorityTypeId: Int? = 0
)
