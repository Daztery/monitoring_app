package com.example.monitoringapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityLoginBinding
import com.example.monitoringapp.util.Constants

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {

            val type = intent.getStringExtra(Constants.KEY_TYPE)

            if (type != null) {
                binding.textType.text = type
            }

            binding.textForgotPassword.setOnClickListener {
                val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }

        }
    }
}