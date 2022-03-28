package com.example.monitoringapp.ui.doctor.searchpatient

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.ActivitySearchPatientBinding
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPatientActivity : AppCompatActivity() {

    private val searchPatientViewModel: SearchPatientViewModel by viewModels()

    private lateinit var binding: ActivitySearchPatientBinding

    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Busqueda de paciente"

        setupObservers()

        userModel = intent.getSerializableExtra(Constants.KEY_USER) as User

        binding.run {
            loadData(userModel)
            buttonEdit.setOnClickListener {
                val intent = Intent(this@SearchPatientActivity, EditPatientActivity::class.java)
                intent.putExtra("update", userModel)
                startActivity(intent)
            }
        }

    }

    private fun loadData(user: User) {
        binding.run {
            textMedicalCenter.text = PreferencesHelper.medicalCenter
            editNameLastname.setText(user.patient?.getFullName())
            editDni.setText(user.identification)
            editAge.setText(user.patient?.getAge().toString())
            editBirthday.setText(user.patient?.getBirthday())
            editMail.setText(user.email)
            editPhone.setText(user.patient?.phone)
            textHeight.text = user.patient?.height.toString()
            textWeight.text = user.patient?.weight.toString()
        }
    }

    private fun setupObservers() {
        searchPatientViewModel.uiViewGetPatientStateObservable.observe(
            this@SearchPatientActivity,
            getPatientObserver
        )
    }

    //Observers
    private val getPatientObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                val userObserver = it.result
                binding.run {
                    //progressBar.gone()
                    loadData(userObserver)
                }
            }
            is UIViewState.Loading -> {
                hideKeyboard()
                //binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                //binding.progressBar.visible()
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        searchPatientViewModel.getPatient(userModel.identification?.toInt() ?: 0)
    }


}