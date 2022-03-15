package com.example.monitoringapp.ui.doctor.medicalconsultation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.ActivityMedicalConsultationDetailBinding
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalConsultationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalConsultationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMedicalConsultationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Consultas Médicas"

        val plan = intent.getSerializableExtra(Constants.KEY_PLAN) as Plan

        binding.run {
            textNumberAttention.text = plan.code.toString()
            textEmergencyType.text = plan.emergencyType?.name
            textStatus.text = plan.patient?.status
            textMonitoringTime.text = "1 semana"
            textPlanMedical.text = ""
        }
    }
}