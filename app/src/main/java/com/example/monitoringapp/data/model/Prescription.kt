package com.example.monitoringapp.data.model

data class Prescription(
    var id: Int? = 0,
    var code: Int? = 0,
    var medicine1: String? = "",
    var medicine2: String? = "",
    var medicine3: String? = "",
    var medicine4: String? = "",
    var medicine5: String? = "",
    var instructions: String? = "",
    var createdAt: String? = "",
    var monitoringPlanId: Int? = 0
)
