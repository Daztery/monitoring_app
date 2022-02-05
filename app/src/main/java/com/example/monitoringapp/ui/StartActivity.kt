package com.example.monitoringapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityStartBinding
import com.example.monitoringapp.ui.auth.LoginActivity
import com.example.monitoringapp.util.Constants

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this@StartActivity, LoginActivity::class.java)

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