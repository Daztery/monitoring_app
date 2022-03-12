package com.example.monitoringapp.ui.patient.dailyreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.TemperatureSaturation
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.DailyReportDateRequest
import com.example.monitoringapp.data.network.request.DailyReportRequest
import com.example.monitoringapp.databinding.FragmentDailyReportBinding
import com.example.monitoringapp.ui.patient.medicalrecord.MedicalRecordViewModel
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DailyReportFragment : Fragment() {

    private val dailyReportViewModel: DailyReportViewModel by viewModels()
    private var _binding: FragmentDailyReportBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        binding.run {
            var flagFrequency = true
            var flagTemperature = true
            var flagOxygenSaturation = true
            var flagGeneralDiscomfort = true
            constraintFrequency.setOnClickListener {
                flagFrequency = if (flagFrequency) {
                    constraintFrequencyBelow.visible()
                    !flagFrequency
                } else {
                    constraintFrequencyBelow.gone()
                    !flagFrequency
                }
            }
            constraintTemperature.setOnClickListener {
                flagTemperature = if (flagTemperature) {
                    constraintTemperatureBelow.visible()
                    !flagTemperature
                } else {
                    constraintTemperatureBelow.gone()
                    !flagTemperature
                }
            }
            constraintOxygenSaturation.setOnClickListener {
                flagOxygenSaturation = if (flagOxygenSaturation) {
                    constraintOxygenSaturationBelow.visible()
                    !flagOxygenSaturation
                } else {
                    constraintOxygenSaturationBelow.gone()
                    !flagOxygenSaturation
                }
            }

            constraintGeneralDiscomfort.setOnClickListener {
                flagGeneralDiscomfort = if (flagGeneralDiscomfort) {
                    constraintGeneralDiscomfortBelow.visible()
                    !flagGeneralDiscomfort
                } else {
                    constraintGeneralDiscomfortBelow.gone()
                    !flagGeneralDiscomfort
                }
            }

            buttonRegister.setOnClickListener {
                if (editTemperature.text.isNotEmpty() &&
                    editFrequency.text.isNotEmpty() &&
                    editOxygenSaturation.text.isNotEmpty()
                ) {
                    dailyReportViewModel.getSelfPlans()
                } else {
                    toast(Constants.DEFAULT_ERROR)
                }
            }
        }
    }

    private fun setupObservers() {
        dailyReportViewModel.uiViewCreateReportStateObservable.observe(
            viewLifecycleOwner,
            createReportObserver
        )
        dailyReportViewModel.uiViewGetSelfPlansStateObservable.observe(
            viewLifecycleOwner,
            getSelfPlansObserver
        )
    }

    //Observers
    private val createReportObserver = Observer<UIViewState<TemperatureSaturation>> {
        when (it) {
            is UIViewState.Success -> {
                binding.progressBar.gone()
                toast("Reporte diario creado")
                binding.run {
                    editFrequency.setText("")
                    editOxygenSaturation.setText("")
                    editTemperature.setText("")
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

    private val getSelfPlansObserver = Observer<UIViewState<Plan>> {
        when (it) {
            is UIViewState.Success -> {
                val planObserver = it.result
                val dailyReportRequest = DailyReportRequest()
                val currentDate = Formatter.formatLocalYearFirstDate(Date())
                binding.run {
                    dailyReportRequest.heartRate = editFrequency.text.toString().toInt()
                    dailyReportRequest.saturation = editOxygenSaturation.text.toString().toInt()
                    dailyReportRequest.temperature = editTemperature.text.toString().toInt()
                    dailyReportRequest.currentDate = currentDate
                    dailyReportViewModel.createReport(planObserver.id ?: 0, dailyReportRequest)
                }
            }
            is UIViewState.Error -> {
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}