package com.example.monitoringapp.data.network.request

data class PlanPrescriptionRequest(
    var code: Int,
    var medicines: List<String>,
    var instructions: String,
)

