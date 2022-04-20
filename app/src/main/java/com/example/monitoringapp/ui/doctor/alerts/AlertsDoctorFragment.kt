package com.example.monitoringapp.ui.doctor.alerts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Alert
import com.example.monitoringapp.databinding.FragmentAlertsDoctorBinding
import com.example.monitoringapp.ui.adapter.AlertsDoctorAdapter
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertsDoctorFragment : Fragment() {

    private val alertsDoctorViewModel: AlertsDoctorViewModel by viewModels()
    private var _binding: FragmentAlertsDoctorBinding? = null
    private val binding get() = _binding!!

    private lateinit var alertsAdapter: AlertsDoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertsDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        alertsDoctorViewModel.getAllAlerts()
        binding.run {
            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    private fun setupObservers() {
        alertsDoctorViewModel.uiViewGetSelfAlertStateObservable.observe(
            viewLifecycleOwner,
            getSelfAlertsObserver
        )
    }

    private val getSelfAlertsObserver = Observer<UIViewState<List<Alert>>> {
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
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}