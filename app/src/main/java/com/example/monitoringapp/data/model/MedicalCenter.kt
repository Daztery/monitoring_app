package com.example.monitoringapp.data.model

import java.io.Serializable

data class MedicalCenter(
    var id: Int? = 0,
    var name: String? = "",
    var address: String? = "",
    var province: String? = "",
    var region: String? = "",
    var district: String? = "",
    var category: String? = "",
):Serializable
