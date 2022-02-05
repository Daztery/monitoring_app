package com.example.monitoringapp.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityHomePatientBinding

class HomePatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}