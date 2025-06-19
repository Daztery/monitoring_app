package com.example.monitoringapp.ui.doctor.medicalconsultation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.databinding.ActivityMedicalConsultationDetailBinding
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.days

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

            if (plan.patient?.status == "DISCHARGED") {
                textStatus.text = "Alta médica"
            } else {
                textStatus.text = "En monitoreo"
            }

            val startDate = Formatter.getLocaleDate(plan.startDate!!)
            val endDate = Formatter.getLocaleDate(plan.endDate!!)
            val rest = (endDate?.time!!).minus((startDate?.time!!))


            val msDiff: Long = endDate.time - startDate.time
            val daysDiff: Long = TimeUnit.MILLISECONDS.toDays(msDiff)

            textMonitoringTime.text = "$daysDiff días"
            textPlanMedical.text = plan.code?.toString()
        }
    }
}