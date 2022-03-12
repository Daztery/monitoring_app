package com.example.monitoringapp.data.model

data class TemperatureSaturation(
    var id: Int? = 0,
    var temperature: String? = "",
    var saturation: String? = "",
    var heartRate: String? = "",
    var createdAt: String? = "",
    var monitoringPlanId: Int? = 0,
)


