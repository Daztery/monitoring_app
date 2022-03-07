package com.example.monitoringapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityStartBinding
import com.example.monitoringapp.ui.auth.LoginActivity
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.ui.patient.HomePatientFragment
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this@StartActivity, LoginActivity::class.java)

        if (PreferencesHelper.type == Constants.PATIENT) {
            val intentPatient = Intent(this@StartActivity, HomePatientActivity::class.java)
            startActivity(intentPatient)
            finish()
        } else if (PreferencesHelper.type ==  Constants.DOCTOR) {
            val intentDoctor = Intent(this@StartActivity, HomeDoctorActivity::class.java)
            startActivity(intentDoctor)
            finish()
        }


        binding.run {

            buttonDoctor.setOnClickListener {
                intent.putExtra(Constants.KEY_TYPE, "Médico")
                startActivity(intent)
            }

            buttonPatient.setOnClickListener {
                intent.putExtra(Constants.KEY_TYPE, "Paciente")
                startActivity(intent)
            }
        }

    }


}