package com.example.monitoringapp.ui.doctor.medicreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.databinding.FragmentMedicReportBinding
import com.example.monitoringapp.ui.adapter.MedicReportAdapter
import com.example.monitoringapp.ui.adapter.PatientHistoryAdapter
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicReportFragment : Fragment() {

    private val medicRecordViewModel: MedicRecordViewModel by viewModels()

    private var _binding: FragmentMedicReportBinding? = null

    private val binding get() = _binding!!

    private lateinit var medicReportAdapter: MedicReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeDoctorActivity).title = "Reporte Médico"

        setupObservers()

        medicRecordViewModel.getSelfPlans()

        binding.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun setupObservers() {
        medicRecordViewModel.uiViewGetSelfPlansStateObservable.observe(
            viewLifecycleOwner,
            getSelfPlansObserver
        )
    }

    //Observers
    private val getSelfPlansObserver = Observer<UIViewState<List<Plan>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                medicReportAdapter = MedicReportAdapter(it.result)
                binding.recycler.adapter = medicReportAdapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}