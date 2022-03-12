package com.example.monitoringapp.ui.patient.information

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.UpdatePatientRequest
import com.example.monitoringapp.databinding.ActivityInformationBinding
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class InformationActivity : AppCompatActivity() {

    private val informationViewModel: InformationViewModel by viewModels()
    private lateinit var binding: ActivityInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra(Constants.KEY_USER) as User

        setupObservers()

        binding.run {
            editDni.setText(user.identification)
            editMail.setText(user.email)
            if (PreferencesHelper.type == Constants.DOCTOR) {
                editFullname.setText(user.doctor?.getFullName())
                editBirthday.setText(user.doctor?.getBirthday())
                editAge.setText(user.doctor?.getAge().toString())
                editCellphone.setText(user.doctor?.phone)
            } else {
                editFullname.setText(user.patient?.getFullName())
                editBirthday.setText(user.patient?.getBirthday())
                editAge.setText(user.patient?.getAge().toString())
                editCellphone.setText(user.patient?.phone)
            }

            buttonSave.setOnClickListener {
                val updateUser = UpdatePatientRequest()
                updateUser.email = editMail.text.toString()
                updateUser.patient = user.patient
                updateUser.patient?.phone = editCellphone.text.toString()
                informationViewModel.updatePatient(user.id!!, updateUser)
            }
        }
    }

    private fun setupObservers() {
        informationViewModel.uiViewUpdatePatientStateObservable.observe(
            this@InformationActivity,
            updatePatientObserver
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

}