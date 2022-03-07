package com.example.monitoringapp.data.network.request

import com.example.monitoringapp.data.model.Doctor
import com.example.monitoringapp.data.model.Patient

data class UpdatePatientRequest(
    var email: String,
    var patient: Patient
)

data class UpdateDoctorRequest(
    var email: String,
    var doctor: Doctor
)
