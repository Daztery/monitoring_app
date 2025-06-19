package com.example.monitoringapp.data.network.request

data class PlanPrescriptionRequest(
    var code: Int = 0,
    var medicines: List<String> = emptyList(),
    var instructions: String = "",
)

