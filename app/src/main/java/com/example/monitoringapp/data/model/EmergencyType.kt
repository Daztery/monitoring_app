package com.example.monitoringapp.data.model

data class Emergency(
    var id: Int? = 0,
    var name: String? = ""
)

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
