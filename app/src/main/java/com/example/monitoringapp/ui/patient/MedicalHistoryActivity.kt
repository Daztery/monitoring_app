package com.example.monitoringapp.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityMedicalHistoryBinding

class MedicalHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}