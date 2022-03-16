package com.example.monitoringapp.ui.doctor.registermonitoringplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.PlanPrescriptionRequest
import com.example.monitoringapp.databinding.ActivityRegisterPrescriptionBinding
import com.example.monitoringapp.ui.adapter.SearchPatientAdapter
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.UIViewState
import com.example.monitoringapp.util.hideKeyboard
import com.example.monitoringapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterPrescriptionActivity : AppCompatActivity() {

    private val registerMonitoringPlanViewModel: RegisterMonitoringPlanViewModel by viewModels()

    private lateinit var binding: ActivityRegisterPrescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Registrar Receta Médica"

        val plan = intent.getSerializableExtra(Constants.KEY_PLAN) as Plan

        setupObservers()
        binding.run {

            editFullname.setText(plan.patient?.getFullName())
            editCodeMonitoring.setText(plan.code.toString())
            editCodePrescription.setText((100..999).random())


            buttonRegister.setOnClickListener {
                if (editCodeMonitoring.text.toString().isNotEmpty() &&
                    editCodePrescription.text.toString().isNotEmpty() &&
                    editFullname.text.toString().isNotEmpty() &&
                    editIndications.text.toString().isNotEmpty() &&
                    editMedicament1.text.toString().isNotEmpty()
                ) {
                    val planPrescriptionRequest = PlanPrescriptionRequest()
                    planPrescriptionRequest.code = editCodePrescription.text.toString().toInt()
                    planPrescriptionRequest.instructions = editIndications.text.toString()
                    val list = mutableListOf<String>()
                    if (editMedicament5.text.toString().isNotEmpty()) {
                        list.add(editMedicament1.text.toString())
                        list.add(editMedicament2.text.toString())
                        list.add(editMedicament3.text.toString())
                        list.add(editMedicament4.text.toString())
                        list.add(editMedicament5.text.toString())
                    } else if (editMedicament4.text.toString().isNotEmpty()) {
                        list.add(editMedicament1.text.toString())
                        list.add(editMedicament2.text.toString())
                        list.add(editMedicament3.text.toString())
                        list.add(editMedicament4.text.toString())
                    } else if (editMedicament3.text.toString().isNotEmpty()) {
                        list.add(editMedicament1.text.toString())
                        list.add(editMedicament2.text.toString())
                        list.add(editMedicament3.text.toString())
                    } else if (editMedicament2.text.toString().isNotEmpty()) {
                        list.add(editMedicament1.text.toString())
                        list.add(editMedicament2.text.toString())
                    } else {
                        list.add(editMedicament1.text.toString())
                    }
                    planPrescriptionRequest.medicines = list
                    registerMonitoringPlanViewModel.createPlanPrescription(
                        plan.id!!,
                        planPrescriptionRequest
                    )
                } else {
                    toast("Completar todos los campos")
                }
            }
        }

    }

    private fun setupObservers() {
        registerMonitoringPlanViewModel.uiViewGetPatientStateObservable.observe(
            this,
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
                    toast("Receta médica registrada")
                    finish()
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
}