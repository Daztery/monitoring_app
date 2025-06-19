package com.example.monitoringapp.data.model

import java.io.Serializable

data class Emergency(
    var id: Int? = 0,
    var name: String? = "",
    var count: Int? = 0
) : Serializable

data class EmergencyType(
    var patient: PatientEmergencyType? = PatientEmergencyType(),
    var emergencyType: Emergency? = Emergency(),
    var startDate: String? = "",
    var endDate: String? = ""
)

data class PatientEmergencyType(
    var firstName: String? = "",
    var lastName: String? = "",
    var id: String? = "",
    var user: UserEmergencyType? = UserEmergencyType(),
) {
    fun getFullName(): String {
        return "$firstName $lastName"
    }
}

data class UserEmergencyType(
    var identification: String? = "",
    var id: Int = 0
)
