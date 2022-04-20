package com.example.monitoringapp.ui.doctor.searchpatient

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.FragmentSearchPatientBinding
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPatientFragment : Fragment() {

    private val searchPatientViewModel: SearchPatientViewModel by viewModels()

    private var _binding: FragmentSearchPatientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchPatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeDoctorActivity).title = "Buscar paciente"
        setupObservers()
        binding.run {
            buttonSearch.setOnClickListener {
                if (editIdentification.text.toString().isNotEmpty()) {
                    searchPatientViewModel.getPatient(editIdentification.text.toString())
                } else {
                    toast("Completar todos los campos")
                }
            }
        }
    }

    private fun setupObservers() {
        searchPatientViewModel.uiViewGetPatientStateObservable.observe(
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
                    val user: User = userObserver
                    val intent = Intent(context, SearchPatientActivity::class.java)
                    intent.putExtra(Constants.KEY_USER, user)
                    startActivity(intent)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}