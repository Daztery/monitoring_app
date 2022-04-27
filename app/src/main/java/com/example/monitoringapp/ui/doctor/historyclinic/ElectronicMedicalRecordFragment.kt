package com.example.monitoringapp.ui.doctor.historyclinic

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.FragmentElectronicMedicalRecordBinding
import com.example.monitoringapp.ui.adapter.SearchPatientAdapter
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.UIViewState
import com.example.monitoringapp.util.hideKeyboard
import com.example.monitoringapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElectronicMedicalRecordFragment : Fragment() {

    private val electronicMedicalRecordViewModel: ElectronicMedicalRecordViewModel by viewModels()

    private var _binding: FragmentElectronicMedicalRecordBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchPatientAdapter: SearchPatientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentElectronicMedicalRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        (activity as HomeDoctorActivity).title = "Historial Clínico Electrónico"

        binding.run {
            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            buttonSearch.setOnClickListener {
                if (editIdentification.text.toString().isNotEmpty()) {
                    electronicMedicalRecordViewModel.getPatient(editIdentification.text.toString())
                } else {
                    toast("Completar todos los campos")
                }
            }
        }
    }

    private fun setupObservers() {
        electronicMedicalRecordViewModel.uiViewGetPatientStateObservable.observe(
            viewLifecycleOwner,
            getPatientObserver
        )
    }

    //Observers
    private val getPatientObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                val userObserver = it.result
                binding.run {
                    //progressBar.gone()
                    val list = listOf(userObserver)
                    searchPatientAdapter =
                        SearchPatientAdapter(list, onClickCallback = { id -> onClickCallback(id) })
                    recycler.adapter = searchPatientAdapter
                }
            }
            is UIViewState.Loading -> {
                hideKeyboard()
                //binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                //binding.progressBar.visible()
                toast(it.message)
            }
        }
    }

    // Callbacks
    private fun onClickCallback(user: User) {
        val intent = Intent(context, ElectronicMedicalRecordDetailActivity::class.java)
        intent.putExtra(Constants.KEY_USER,user)
        startActivity(intent)
    }

}