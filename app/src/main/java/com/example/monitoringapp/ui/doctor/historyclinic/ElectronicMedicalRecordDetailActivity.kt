package com.example.monitoringapp.ui.doctor.historyclinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.ActivityElectronicMedicalRecordDetailBinding
import com.example.monitoringapp.ui.doctor.medicalconsultation.MedicalConsultationActivity
import com.example.monitoringapp.ui.doctor.medicalmonitoring.MedicalMonitoringActivity
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.util.Constants

class ElectronicMedicalRecordDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityElectronicMedicalRecordDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElectronicMedicalRecordDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Historial Clínico Electrónico"

        val user = intent.getSerializableExtra(Constants.KEY_USER) as User

        binding.run {

            textFullname.text = user.patient?.getFullName()
            textDni.text = user.identification
            textType.text = "Paciente"

            constraintMedicalConsultation.setOnClickListener {
                val intent = Intent(applicationContext, MedicalConsultationActivity::class.java)
                intent.putExtra(Constants.KEY_USER, user)
                startActivity(intent)
            }

            constraintMedicalMonitoring.setOnClickListener {
                val intent = Intent(applicationContext, MedicalMonitoringActivity::class.java)
                intent.putExtra(Constants.KEY_USER, user)
                startActivity(intent)
            }

            constraintPrescription.setOnClickListener {
                //val intent = Intent(applicationContext, MedicalConsultationActivity::class.java)
                //startActivity(intent)
            }
        }
    }

}