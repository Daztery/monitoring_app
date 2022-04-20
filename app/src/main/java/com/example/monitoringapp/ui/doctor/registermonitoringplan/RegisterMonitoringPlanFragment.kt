package com.example.monitoringapp.ui.doctor.registermonitoringplan

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
import com.example.monitoringapp.databinding.FragmentRegisterMonitoringPlanBinding
import com.example.monitoringapp.ui.adapter.SearchPatientAdapter
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.ui.doctor.historyclinic.ElectronicMedicalRecordDetailActivity
import com.example.monitoringapp.ui.doctor.historyclinic.ElectronicMedicalRecordViewModel
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.UIViewState
import com.example.monitoringapp.util.hideKeyboard
import com.example.monitoringapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterMonitoringPlanFragment : Fragment() {

    private val registerMonitoringPlanViewModel: RegisterMonitoringPlanViewModel by viewModels()

    private var _binding: FragmentRegisterMonitoringPlanBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchPatientAdapter: SearchPatientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterMonitoringPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeDoctorActivity).title = "Registrar Plan de Monitoreo"

        setupObservers()

        binding.run {
            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            buttonSearch.setOnClickListener {
                if (editIdentifier.text.toString().isNotEmpty()) {
                    registerMonitoringPlanViewModel.getPatient(editIdentifier.text.toString())
                } else {
                    toast("Completar todos los campos")
                }
            }
        }
    }


    private fun setupObservers() {
        registerMonitoringPlanViewModel.uiViewGetPatientStateObservable.observe(
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
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    //Callbacks
    private fun onClickCallback(user: User) {
        val intent = Intent(context, RegisterMonitoringPlanDetailActivity::class.java)
        intent.putExtra(Constants.KEY_USER,user)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}