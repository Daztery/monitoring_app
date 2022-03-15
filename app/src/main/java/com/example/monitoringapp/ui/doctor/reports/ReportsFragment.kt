package com.example.monitoringapp.ui.doctor.reports

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.databinding.FragmentReportsBinding
import com.example.monitoringapp.ui.adapter.EmergencyReportAdapter
import com.example.monitoringapp.ui.adapter.PatientStatusAdapter
import com.example.monitoringapp.ui.adapter.PatientsByPriorityAdapter
import com.example.monitoringapp.ui.adapter.PriorityReportAdapter
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.ui.doctor.patientspriority.PatientsPriorityViewModel
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.Formatter
import com.example.monitoringapp.util.UIViewState
import com.example.monitoringapp.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReportsFragment : Fragment() {

    private val reportsViewModel: ReportsViewModel by viewModels()

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!

    private lateinit var priorityReportAdapter: PriorityReportAdapter
    private lateinit var emergencyReportAdapter: EmergencyReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeDoctorActivity).title = "Reportes"
        setupObservers()

        binding.run {

            recyclerEmergency.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            recyclerPriority.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            reportsViewModel.getEmergencyReport(Date().time.toString())
            reportsViewModel.getPriorityReport(Date().time.toString())
        }
    }

    private fun setupObservers() {
        reportsViewModel.uiViewGetEmergencyReportStateObservable.observe(
            viewLifecycleOwner,
            getEmergencyReportObserver
        )

        reportsViewModel.uiViewGetPriorityReportStateObservable.observe(
            viewLifecycleOwner,
            getPriorityReportObserver
        )
    }

    //Observers
    private val getEmergencyReportObserver = Observer<UIViewState<List<Report>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val itemObserver = it.result
                binding.run {
                    emergencyReportAdapter = EmergencyReportAdapter(itemObserver)
                    recyclerEmergency.adapter = emergencyReportAdapter
                }
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

    private val getPriorityReportObserver = Observer<UIViewState<List<Report>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val itemObserver = it.result
                binding.run {
                    priorityReportAdapter = PriorityReportAdapter(itemObserver)
                    recyclerPriority.adapter = priorityReportAdapter
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}