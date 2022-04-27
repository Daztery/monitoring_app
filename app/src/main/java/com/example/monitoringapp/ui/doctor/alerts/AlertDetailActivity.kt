package com.example.monitoringapp.ui.doctor.alerts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Alert
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.ActivityAlertDetailBinding
import com.example.monitoringapp.ui.adapter.AlertsDoctorAdapter
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlertDetailBinding

    private val alertsDoctorViewModel: AlertsDoctorViewModel by viewModels()

    private lateinit var alertsAdapter: AlertsDoctorAdapter
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Alertas"

        user = intent.getSerializableExtra(Constants.KEY_USER) as User

        setupObservers()
        alertsDoctorViewModel.getAlertsFromPatient(user.id!!)
        binding.run {
            recycler.layoutManager =
                LinearLayoutManager(this@AlertDetailActivity, LinearLayoutManager.VERTICAL, false)
            textName.text=user.patient?.getFullName()
        }

    }

    private fun setupObservers() {
        alertsDoctorViewModel.uiViewGetAlertsFromPatientStateObservable.observe(
            this@AlertDetailActivity,
            getAlertsFromPatientObserver
        )
    }

    private val getAlertsFromPatientObserver = Observer<UIViewState<List<Alert>>> {
        when (it) {
            is UIViewState.Success -> {
                binding.progressBar.gone()
                val itemObserver = it.result
                alertsAdapter = AlertsDoctorAdapter(itemObserver)
                binding.recycler.adapter = alertsAdapter
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
}