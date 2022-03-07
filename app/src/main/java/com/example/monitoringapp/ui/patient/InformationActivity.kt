package com.example.monitoringapp.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityInformationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}