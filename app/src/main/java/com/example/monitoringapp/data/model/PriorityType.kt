package com.example.monitoringapp.data.model

import java.io.Serializable

data class Priority(
    var id: Int? = 0,
    var name: String? = "",
    var count: Int? = 0
): Serializable

data class Report(
    var id: Int? = 0,
    var name: String? = "",
    var count: Int? = 0,
)

data class PriorityType(
    var patient: PatientPriorityType? = PatientPriorityType(),
    var priority: Priority? = Priority(),
    var startDate: String? = "",
    var endDate: String? = ""
)

data class PatientPriorityType(
    var firstName: String? = "",
    var lastName: String? = "",
    var id: String? = "",
    var user: UserPriorityType? = UserPriorityType(),
) {
    fun getFullName(): String {
        return "$firstName $lastName"
    }
}

data class UserPriorityType(
    var identification: String? = "",
    var id: Int = 0
)

data class ReportStatus(
    var status: String? = "",
    var total: Int? = 0,
)