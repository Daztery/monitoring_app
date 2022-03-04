package com.example.monitoringapp.ui.doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.monitoringapp.databinding.ActivityMedicalConsultationDetailBinding

class MedicalConsultationDetailActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMedicalConsultationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMedicalConsultationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}