package com.example.monitoringapp.ui.patient.medicalrecord

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.FragmentMedicalRecordBinding
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
        setupObservers()
        medicalRecordViewModel.getSelf()
    }

    private fun setupObservers() {
        medicalRecordViewModel.uiViewGetSelfStateObservable.observe(
            viewLifecycleOwner,
            getSelfObserver
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
                    textFullname.text =
                        "${userObserver.patient!!.firstName} ${userObserver.patient!!.lastName}"
                    textDni.text = userObserver.identification
                    textType.text = Constants.PATIENT_TEXT
                    textTypeBlood.text = userObserver.patient?.bloodType
                    textWeight.text = userObserver.patient?.weight.toString()
                    textHeight.text = userObserver.patient?.height.toString()
                    textTemperature.text = "36°"
                    textPercentage.text = "96%"

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}