package com.example.monitoringapp.data.model

import java.io.Serializable

data class Plan(
    var id: Int? = 0,
    var code: Int? = 0,
    var startDate: String? = "",
    var endDate: String? = "",
    var patientId: Int? = 0,
    var doctorId: Int? = 0,
    var emergencyTypeId: Int? = 0,
    var priorityTypeId: Int? = 0,
    var doctor: Doctor? = Doctor(),
    var patient: Patient? = Patient(),
    var emergencyType: Emergency? = Emergency(),
    var priority: Priority? = Priority(),
) : Serializable


