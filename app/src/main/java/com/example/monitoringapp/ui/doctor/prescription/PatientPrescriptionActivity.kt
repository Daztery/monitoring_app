package com.example.monitoringapp.ui.doctor.prescription

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityMedicalConsultationBinding
import com.example.monitoringapp.databinding.ActivityPatientPrescriptionBinding
import com.example.monitoringapp.ui.adapter.PatientStatusAdapter
import com.example.monitoringapp.ui.adapter.PrescriptionAdapter
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity

class PatientPrescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientPrescriptionBinding

    private lateinit var prescriptionAdapter: PrescriptionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Receta Médica"

    }
}