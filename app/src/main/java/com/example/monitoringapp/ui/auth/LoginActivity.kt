package com.example.monitoringapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityLoginBinding
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.util.Constants

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {

            val type = intent.getStringExtra(Constants.KEY_TYPE)
            textType.text = type

            textForgotPassword.setOnClickListener {
                val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }

            buttonLogin.setOnClickListener {
                if (type == "Paciente") {
                    val intent = Intent(this@LoginActivity, HomePatientActivity::class.java)
                    startActivity(intent)
                }
            }



        }
    }
}