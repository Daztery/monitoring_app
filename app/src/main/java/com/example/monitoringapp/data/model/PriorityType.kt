package com.example.monitoringapp.data.model

data class PriorityType(
    var id: Int? = 0,
    var name: String? = ""
)

data class Report(
    var id: Int? = 0,
    var name: String? = "",
    var count: Int? = 0,
)
