package com.example.monitoringapp.ui.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityEditPatientBinding
import com.example.monitoringapp.databinding.ActivitySearchPatientBinding

class EditPatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}