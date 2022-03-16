package com.example.monitoringapp.ui.patient.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.TemperatureSaturation
import com.example.monitoringapp.data.network.request.DailyReportDateRequest
import com.example.monitoringapp.databinding.FragmentHomePatientBinding
import com.example.monitoringapp.ui.adapter.PlanAdapter
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.ui.patient.dailyreport.DailyReportFragment
import com.example.monitoringapp.ui.patient.medicalhistory.MedicalHistoryViewModel
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomePatientFragment : Fragment() {

    private val homePatientViewModel: HomePatientViewModel by viewModels()

    private var _binding: FragmentHomePatientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomePatientActivity).title = "Inicio"

        setupObservers()

        homePatientViewModel.getSelfPlans()

        binding.run {
            textRegisterReport.setOnClickListener {
                replaceFragment(DailyReportFragment())
            }
        }
    }

    private fun setupObservers() {
        homePatientViewModel.uiViewGetSelfPlansStateObservable.observe(
            viewLifecycleOwner,
            getSelfPlansObserver
        )

        homePatientViewModel.uiViewGetTemperatureAndSaturationStateObservable.observe(
            viewLifecycleOwner,
            getTemperatureAndSaturationObserver
        )
    }

    //Observers
    private val getSelfPlansObserver = Observer<UIViewState<Plan>> {
        when (it) {
            is UIViewState.Success -> {
                binding.progressBar.gone()
                val planObserver = it.result
                val currentDate = Formatter.formatLocalYearFirstDate(Date())
                val dailyReportDateRequest = DailyReportDateRequest(currentDate)
                homePatientViewModel.getTemperatureAndSaturation(
                    planObserver.id ?: 0,
                    dailyReportDateRequest
                )

                (activity as HomePatientActivity).title = planObserver.patient?.getFullName()
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

    private val getTemperatureAndSaturationObserver = Observer<UIViewState<TemperatureSaturation>> {
        when (it) {
            is UIViewState.Success -> {
                val itemObserver = it.result
                binding.run {
                    textTemperature.text = itemObserver.temperature
                    textSaturationOxygen.text = itemObserver.saturation

                    progressTemperature.setProgress(itemObserver.temperature!!.toFloat(), false, 3)
                    progressSaturationOxygen.setProgress(itemObserver.saturation!!.toFloat(), false, 3)
                }
            }
            is UIViewState.Error -> {
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_patient_fragment, fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}