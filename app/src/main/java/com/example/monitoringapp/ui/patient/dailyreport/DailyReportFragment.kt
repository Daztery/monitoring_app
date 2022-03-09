package com.example.monitoringapp.ui.patient.dailyreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.FragmentDailyReportBinding
import com.example.monitoringapp.ui.patient.medicalrecord.MedicalRecordViewModel
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyReportFragment : Fragment() {


    private val medicalRecordViewModel: MedicalRecordViewModel by viewModels()
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
                }else{
                    constraintFrequencyBelow.gone()
                    !flagFrequency
                }
            }
            constraintTemperature.setOnClickListener {
                flagTemperature = if (flagTemperature) {
                    constraintTemperatureBelow.visible()
                    !flagTemperature
                }else{
                    constraintTemperatureBelow.gone()
                    !flagTemperature
                }
            }
            constraintOxygenSaturation.setOnClickListener {
                flagOxygenSaturation = if (flagOxygenSaturation) {
                    constraintOxygenSaturationBelow.visible()
                    !flagOxygenSaturation
                }else{
                    constraintOxygenSaturationBelow.gone()
                    !flagOxygenSaturation
                }
            }

            constraintGeneralDiscomfort.setOnClickListener {
                flagGeneralDiscomfort = if (flagGeneralDiscomfort) {
                    constraintGeneralDiscomfortBelow.visible()
                    !flagGeneralDiscomfort
                }else{
                    constraintGeneralDiscomfortBelow.gone()
                    !flagGeneralDiscomfort
                }
            }

            buttonRegister.setOnClickListener {

            }
        }
    }

    private fun setupObservers() {
        medicalRecordViewModel.uiViewGetSelfStateObservable.observe(
            viewLifecycleOwner,
            getSelfObserver
        )
    }

    //Observers
    private val getSelfObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {

            }
            is UIViewState.Loading -> {
                /*binding.run {
                    progressBar.visible()
                    constraint.gone()
                }*/
            }
            is UIViewState.Error -> {
                /*binding.run {
                    progressBar.visible()
                    constraint.gone()
                }*/
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}