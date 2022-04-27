package com.example.monitoringapp.ui.doctor.medicalmonitoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.Status
import com.example.monitoringapp.data.model.TemperatureSaturation
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.DailyReportDateRequest
import com.example.monitoringapp.databinding.ActivityMedicalMonitoringBinding
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class MedicalMonitoringActivity : AppCompatActivity() {

    private val medicalMonitoringViewModel: MedicalMonitoringViewModel by viewModels()

    private lateinit var binding: ActivityMedicalMonitoringBinding

    private lateinit var user: User
    private var monitoringPlanId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalMonitoringBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Seguimiento Médico"

        user = intent.getSerializableExtra(Constants.KEY_USER) as User

        setupObservers()

        val currentDate = DataUtil.getCurrentDate().time
        medicalMonitoringViewModel.getPatientStatus(currentDate.toString(), currentDate.toString())

        binding.run {
            editHeight.setText(user.patient?.height.toString())
            editWeight.setText(user.patient?.weight.toString())
        }

    }

    private fun setupObservers() {
        medicalMonitoringViewModel.uiViewGetFromPatientStateObservable.observe(
            this,
            getReportFromPatientObserver
        )
        medicalMonitoringViewModel.uiViewGetPatientStatusStateObservable.observe(
            this,
            getPatientStatusObserver
        )
    }

    //Observers
    private val getReportFromPatientObserver = Observer<UIViewState<TemperatureSaturation>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val itemObserver=it.result
                binding.run {
                    editTemperature.setText(itemObserver.temperature)
                    editOxygenSaturation.setText(itemObserver.saturation)
                    editFrequency.setText(itemObserver.heartRate)
                    editGeneralDiscomfort.setText(itemObserver.discomfort)
                }
            }
            is UIViewState.Loading -> {
                //binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                //binding.progressBar.gone()
                toast(it.message)
            }
        }
    }

    private val getPatientStatusObserver = Observer<UIViewState<List<Status>>> {
        when (it) {
            is UIViewState.Success -> {
                val patientStatusObserver = it.result
                for (item in patientStatusObserver) {
                    if (user.id == item.userId) monitoringPlanId = item.monitoringPlanId!!
                }
                val currentDate = Formatter.formatLocalYearFirstDate(DataUtil.getCurrentDate())
                val dailyReportDateRequest = DailyReportDateRequest(currentDate)
                medicalMonitoringViewModel.getReportFromPatient(
                    monitoringPlanId,
                    user.id!!,
                    dailyReportDateRequest
                )
            }
            is UIViewState.Error -> {
                toast(it.message)
            }
        }
    }
}