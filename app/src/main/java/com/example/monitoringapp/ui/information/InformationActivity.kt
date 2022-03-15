package com.example.monitoringapp.ui.information

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.UpdateDoctorRequest
import com.example.monitoringapp.data.network.request.UpdatePatientRequest
import com.example.monitoringapp.databinding.ActivityInformationBinding
import com.example.monitoringapp.ui.patient.information.InformationViewModel
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationActivity : AppCompatActivity() {

    private val informationViewModel: InformationViewModel by viewModels()
    private lateinit var binding: ActivityInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.title = "Informacion del paciente"
        val user = intent.getSerializableExtra(Constants.KEY_USER) as User

        setupObservers()

        binding.run {
            editDni.setText(user.identification)
            editMail.setText(user.email)
            if (PreferencesHelper.type == Constants.DOCTOR) {
                textTitle.text = "Información del Médico"
                editFullname.setText(user.doctor?.getFullName())
                editBirthday.setText(user.doctor?.getBirthday())
                editAge.setText(user.doctor?.getAge().toString())
                editCellphone.setText(user.doctor?.phone)
            } else {
                textTitle.text = "Información del Paciente"
                editFullname.setText(user.patient?.getFullName())
                editBirthday.setText(user.patient?.getBirthday())
                editAge.setText(user.patient?.getAge().toString())
                editCellphone.setText(user.patient?.phone)
            }

            buttonSave.setOnClickListener {
                if (PreferencesHelper.type == Constants.DOCTOR) {
                    val updateUser = UpdateDoctorRequest()
                    updateUser.email = editMail.text.toString()
                    updateUser.doctor = user.doctor
                    updateUser.doctor?.phone = editCellphone.text.toString()
                    informationViewModel.updateDoctor(user.id!!, updateUser)
                } else {
                    val updateUser = UpdatePatientRequest()
                    updateUser.email = editMail.text.toString()
                    updateUser.patient = user.patient
                    updateUser.patient?.phone = editCellphone.text.toString()
                    informationViewModel.updatePatient(user.id!!, updateUser)
                }
            }
        }
    }

    private fun setupObservers() {
        informationViewModel.uiViewUpdatePatientStateObservable.observe(
            this@InformationActivity,
            updatePatientObserver
        )

        informationViewModel.uiViewUpdateDoctorStateObservable.observe(
            this@InformationActivity,
            updateDoctortObserver
        )
    }

    //Observers
    private val updatePatientObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                toast("Cambio Exitoso")
                finish()
            }
            is UIViewState.Error -> {
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    //Observers
    private val updateDoctortObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                toast("Cambio Exitoso")
                finish()
            }
            is UIViewState.Error -> {
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

}