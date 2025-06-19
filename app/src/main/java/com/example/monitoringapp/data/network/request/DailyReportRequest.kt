package com.example.monitoringapp.data.network.request

data class DailyReportRequest(
    var currentDate: String? = "",
    var heartRate: Double? = 0.0,
    var saturation: Double? = 0.0,
    var temperature: Double? = 0.0,
    var discomfort: String? = ""
)

data class DailyReportDateRequest(
    var currentDate: String? = ""
)
