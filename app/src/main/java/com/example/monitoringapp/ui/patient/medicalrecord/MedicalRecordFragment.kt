package com.example.monitoringapp.ui.patient.medicalrecord

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
import com.example.monitoringapp.databinding.FragmentMedicalRecordBinding
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MedicalRecordFragment : Fragment() {

    private val medicalRecordViewModel: MedicalRecordViewModel by viewModels()
    private var _binding: FragmentMedicalRecordBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicalRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomePatientActivity).title = "Ficha Médica"
        setupObservers()
        medicalRecordViewModel.getSelf()
    }

    private fun setupObservers() {
        medicalRecordViewModel.uiViewGetSelfStateObservable.observe(
            viewLifecycleOwner,
            getSelfObserver
        )
        medicalRecordViewModel.uiViewGetSelfPlansStateObservable.observe(
            viewLifecycleOwner,
            getSelfPlansObserver
        )
        medicalRecordViewModel.uiViewGetTemperatureAndSaturationStateObservable.observe(
            viewLifecycleOwner,
            getTemperatureAndSaturationObserver
        )
    }

    //Observers
    private val getSelfObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                val userObserver = it.result
                binding.run {
                    progressBar.gone()
                    constraint.visible()
                    textFullname.text = userObserver.patient?.getFullName()
                    textDni.text = userObserver.identification
                    textType.text = Constants.PATIENT_TEXT
                    textTypeBlood.text = userObserver.patient?.bloodType
                    textWeight.text = userObserver.patient?.weight.toString()
                    textHeight.text = userObserver.patient?.height.toString()
                    medicalRecordViewModel.getSelfPlans()
                }
            }
            is UIViewState.Loading -> {
                binding.run {
                    progressBar.visible()
                    constraint.gone()
                }
            }
            is UIViewState.Error -> {
                binding.run {
                    progressBar.visible()
                    constraint.gone()
                }
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    private val getSelfPlansObserver = Observer<UIViewState<Plan>> {
        when (it) {
            is UIViewState.Success -> {
                binding.progressBar.gone()
                val planObserver = it.result
                val currentDate = Formatter.formatLocalYearFirstDate(Date())
                val dailyReportDateRequest = DailyReportDateRequest(currentDate)
                medicalRecordViewModel.getTemperatureAndSaturation(
                    planObserver.id ?: 0,
                    dailyReportDateRequest
                )
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
                    textTemperature.text = itemObserver.temperature + "°"
                    textSaturationOxygen.text = itemObserver.saturation + "%"
                }
            }
            is UIViewState.Error -> {
                toast("No se encontró reporte diario")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}