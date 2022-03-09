package com.example.monitoringapp.data.network.request

data class PlanRequest(
    var patientId: Int? = 0,
    var code: Int? = 0,
    var endDate: String? = "",
    var startDate: String? = "",
    var emergencyTypeId: Int? = 0,
    var priorityTypeId: Int? = 0
)
