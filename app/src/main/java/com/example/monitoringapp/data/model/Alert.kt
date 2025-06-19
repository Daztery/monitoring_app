package com.example.monitoringapp.data.model

data class Alert(
    var id: Int? = 0,
    var createdAt: String? = "",
    var alertType: String? = "",
    var dailyReportId: Int? = 0,
    var dailyReport: AlertDailyReport? = AlertDailyReport()
)

data class AlertDailyReport(
    var id: Int? = 0,
    var temperature: String? = "",
    var saturation: String? = "",
    var heartRate: String? = "",
    var createdAt: String? = "",
    var monitoringPlanId: Int? = 0,
    var monitoringPlan: AlertMonitoringPlan? = AlertMonitoringPlan()
)

data class AlertMonitoringPlan(
    var emergencyType: Report? = Report(),
    var priority: Report? = Report(),
    var doctor: AlertDoctor? = AlertDoctor(),
    var patient: AlertPatient? = AlertPatient(),
)

data class AlertDoctor(
    var id: Int? = 0,
    var firstName: String? = "",
    var lastName: String? = "",
    var phone: String? = "",
){
    fun getFullName(): String {
        return "$firstName $lastName"
    }
}

data class AlertPatient(
    var id: Int? = 0,
    var firstName: String? = "",
    var lastName: String? = "",
    var phone: String? = "",
){
    fun getFullName(): String {
        return "$firstName $lastName"
    }
}
