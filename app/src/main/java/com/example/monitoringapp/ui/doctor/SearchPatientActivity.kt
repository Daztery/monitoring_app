package com.example.monitoringapp.ui.doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.monitoringapp.databinding.ActivitySearchPatientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}