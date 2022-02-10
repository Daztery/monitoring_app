package com.example.monitoringapp.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityDailyReportBinding

class DailyReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDailyReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDailyReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}