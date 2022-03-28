package com.example.monitoringapp.ui.doctor.medicalmonitoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.TemperatureSaturation
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.DailyReportDateRequest
import com.example.monitoringapp.databinding.ActivityMedicalMonitoringBinding
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MedicalMonitoringActivity : AppCompatActivity() {

    private val medicalMonitoringViewModel: MedicalMonitoringViewModel by viewModels()

    private lateinit var binding: ActivityMedicalMonitoringBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalMonitoringBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Seguimiento Médico"

        val user = intent.getSerializableExtra(Constants.KEY_USER) as User

        setupObservers()

        medicalMonitoringViewModel.getSelfPlans()

        binding.run {
            editHeight.setText(user.patient?.height.toString())
            editWeight.setText(user.patient?.weight.toString())
        }

    }

    private fun setupObservers() {
        medicalMonitoringViewModel.uiViewGetTemperatureAndSaturationStateObservable.observe(
            this,
            getTemperatureAndSaturationObserver
        )
        medicalMonitoringViewModel.uiViewGetSelfPlansStateObservable.observe(
            this,
            getSelfPlansObserver
        )
    }

    //Observers
    private val getSelfPlansObserver = Observer<UIViewState<Plan>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val planObserver = it.result
                val currentDate = Formatter.formatLocalYearFirstDate(Date())
                val dailyReportDateRequest = DailyReportDateRequest(currentDate)
                medicalMonitoringViewModel.getTemperatureAndSaturation(
                    planObserver.id ?: 0,
                    dailyReportDateRequest
                )
            }
            is UIViewState.Loading -> {
                //binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                //binding.progressBar.gone()
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    private val getTemperatureAndSaturationObserver = Observer<UIViewState<TemperatureSaturation>> {
        when (it) {
            is UIViewState.Success -> {
                val itemObserver = it.result
                binding.run {
                    editTemperature.setText(itemObserver.temperature)
                    editOxygenSaturation.setText(itemObserver.saturation)
                    editFrequency.setText(itemObserver.heartRate)
                }
            }
            is UIViewState.Error -> {
                //toast(Constants.DEFAULT_ERROR)
            }
        }
    }
}