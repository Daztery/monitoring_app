package com.example.monitoringapp.data.network.request

data class DailyReportRequest(
    var currentDate: String? = "",
    var heartRate: Int? = 0,
    var saturation: Int? = 0,
    var temperature: Int? = 0
)

data class DailyReportDateRequest(
    var currentDate: String? = ""
)
