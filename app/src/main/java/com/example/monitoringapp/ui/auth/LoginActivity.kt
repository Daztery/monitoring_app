package com.example.monitoringapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.MedicalCenter
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.SignInRequest
import com.example.monitoringapp.databinding.ActivityLoginBinding
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()

        binding.run {

            type = intent.getStringExtra(Constants.KEY_TYPE) ?: ""
            textType.text = type

            textForgotPassword.setOnClickListener {
                val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }

            buttonLogin.setOnClickListener {
                login(editDni.text.toString(), editPassword.text.toString())
            }

        }
    }

    private fun setupObservers() {
        authenticationViewModel.uiViewLoginDoctorStateObservable.observe(
            this@LoginActivity,
            loginDoctorObserver
        )

        authenticationViewModel.uiViewLoginPatientStateObservable.observe(
            this@LoginActivity,
            loginPatientObserver
        )

        authenticationViewModel.uiViewGetMedicalCenterStateObservable.observe(
            this@LoginActivity,
            medicalCenterObserver
        )
    }

    private fun login(email: String, password: String) {
        binding.run {
            if (email.isNotBlank() && password.isNotBlank()) {
                val user = SignInRequest(
                    identification = editDni.text.toString().trim(),
                    password = editPassword.text.toString()
                )
                if (type == "Médico") {
                    authenticationViewModel.loginDoctor(user)
                } else {
                    authenticationViewModel.loginPatient(user)
                }
            } else {
                toast(resources.getText(R.string.text_missing_email_or_password).toString())
            }
        }
    }

    private val loginDoctorObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                binding.progressBar.gone()
                val userData = it.result

                PreferencesHelper.token = userData.token
                PreferencesHelper.type = "Doctor"
                PreferencesHelper.userData = DataUtil.stringify(userData)
                authenticationViewModel.getMedicalCenter(userData.doctor?.medicalCenterId!!)
            }
            is UIViewState.Loading -> {
                binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                binding.progressBar.gone()
                toast(resources.getText(R.string.text_login_error).toString())
            }
        }
    }

    private val loginPatientObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                binding.progressBar.gone()
                val userData = it.result

                PreferencesHelper.token = userData.token
                PreferencesHelper.type = "Patient"
                PreferencesHelper.userData = DataUtil.stringify(userData)

                val intent = Intent(this@LoginActivity, HomePatientActivity::class.java)
                startActivity(intent)
                finish()
            }
            is UIViewState.Loading -> {
                binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                binding.progressBar.gone()
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    private val medicalCenterObserver = Observer<UIViewState<MedicalCenter>> {
        when (it) {
            is UIViewState.Success -> {
                binding.progressBar.gone()
                val itemObserver = it.result
                PreferencesHelper.medicalCenter = itemObserver.name
                Log.i("MedicalCenter",PreferencesHelper.medicalCenter.toString())

                val intent = Intent(this@LoginActivity, HomeDoctorActivity::class.java)
                startActivity(intent)
                finish()
            }
            is UIViewState.Loading -> {
                binding.progressBar.visible()
            }
            is UIViewState.Error -> {
            }
        }
    }



}