package com.example.monitoringapp.ui.doctor.alerts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Alert
import com.example.monitoringapp.databinding.FragmentAlertsDoctorBinding
import com.example.monitoringapp.ui.adapter.AlertsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertsDoctorFragment : Fragment() {

    private var _binding: FragmentAlertsDoctorBinding? = null
    private val binding get() = _binding!!

    private lateinit var alertsAdapter: AlertsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertsDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            mockAlert()
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