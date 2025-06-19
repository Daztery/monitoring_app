package com.example.monitoringapp.data.model

data class Status(
    var monitoringPlanId: Int? = 0,
    var patientId: Int? = 0,
    var userId: Int? = 0,
    var date: String? = "",
    var firstName: String? = "",
    var lastName: String? = "",
    var status: String? = ""
){
    fun getFullName(): String {
        return "$firstName $lastName"
    }
}
