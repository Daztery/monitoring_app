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
import com.example.monitoringapp.data.model.MedicalCenter
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

        homePatientViewModel.uiViewGetMedicalCenterStateObservable.observe(
            viewLifecycleOwner,
            getMedicalCenterObserver
        )
    }

    //Observers
    private val getSelfPlansObserver = Observer<UIViewState<Plan>> {
        when (it) {
            is UIViewState.Success -> {
                binding.progressBar.gone()
                val planObserver = it.result
                binding.run {
                    textDoctor.text = planObserver.doctor?.getFullName()
                    textEmergencyType.text = planObserver.emergencyType?.name
                    textPriority.text = planObserver.priority?.name
                    val startDate = Formatter.getLocaleDate(planObserver.startDate!!)
                    val endDate = Formatter.getLocaleDate(planObserver.endDate!!)
                    textStartDate.text = Formatter.formatLocalDate(startDate ?: Date())
                    textEndDate.text = Formatter.formatLocalDate(endDate ?: Date())
                }
                planObserver.doctor?.medicalCenterId?.let { it1 ->
                    homePatientViewModel.getMedicalCenter(
                        it1
                    )
                }


                (activity as HomePatientActivity).title = planObserver.patient?.getFullName()
            }
            is UIViewState.Loading -> {
                binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                binding.progressBar.gone()
                toast(it.message)
            }
        }
    }

    private val getMedicalCenterObserver = Observer<UIViewState<MedicalCenter>> {
        when (it) {
            is UIViewState.Success -> {
                val itemObserver = it.result
                binding.run {
                    textMedicalCenter.text = itemObserver.name
                }
            }
            is UIViewState.Error -> {
                //toast("No se encontró reporte diario")
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