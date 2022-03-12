package com.example.monitoringapp.ui.patient.alerts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Alert
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.FragmentAlertsBinding
import com.example.monitoringapp.ui.adapter.AlertsAdapter
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.ui.patient.medicalrecord.MedicalRecordViewModel
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertsFragment : Fragment() {

    private val alertsViewModel: AlertsViewModel by viewModels()
    private var _binding: FragmentAlertsBinding? = null
    private val binding get() = _binding!!

    private lateinit var alertsAdapter: AlertsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomePatientActivity).title = "Alertas"
        setupObservers()
        alertsViewModel.getSelf()
        binding.run {
            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            mockAlert()
        }
    }

    private fun setupObservers() {
        alertsViewModel.uiViewGetSelfStateObservable.observe(
            viewLifecycleOwner,
            getSelfObserver
        )
    }

    //Observers
    private val getSelfObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                binding.progressBar.gone()
                val userObserver = it.result
                binding.run {
                    textFullname.text = userObserver.patient?.getFullName()
                    textDni.text = userObserver.identification
                    textType.text = Constants.PATIENT_TEXT
                }
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

    private fun mockAlert() {
        val alert1 = Alert("Alerta de Temperatura", "Andres Lopez", "Temperatura 39°")
        val alert2 = Alert("Alerta de Saturación", "Andres Lopez", "Saturación 85%")
        val alert3 = Alert(
            "Alerta de Ausencia de Reporte",
            "Andres Lopez",
            "El paciente no ha realizado su reporte diario"
        )
        val alerts = listOf(alert1, alert2, alert3)
        alertsAdapter = AlertsAdapter(alerts)
        binding.recycler.adapter = alertsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}