package com.example.monitoringapp.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityInformationBinding
import com.example.monitoringapp.databinding.ActivityMedicalRecordBinding

class MedicalRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}