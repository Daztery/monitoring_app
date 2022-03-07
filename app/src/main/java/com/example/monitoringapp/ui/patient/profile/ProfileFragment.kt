package com.example.monitoringapp.ui.patient.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.FragmentProfileBinding
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Constants.PATIENT
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        profileViewModel.getSelf()
    }

    private fun setupObservers() {
        profileViewModel.uiViewGetSelfStateObservable.observe(
            viewLifecycleOwner,
            getSelfObserver
        )
    }

    //Observers
    private val getSelfObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                val user = it.result
                binding.run {
                    if (PreferencesHelper.type == PATIENT){
                        textFullname.text = "${user.patient!!.firstName} ${user.patient!!.lastName}"
                        textDni.text = user.identification
                        textGender.text = "Masculino"
                        val date= Formatter.getLocaleDate(user.patient?.birthdate ?: "")
                        textAge.text = Formatter.formatLocalDate(date ?: Date())
                        textCellphone.text = user.patient?.phone
                        textEmail.text = user.email
                    }else{
                        textFullname.text = "${user.doctor?.firstName} ${user.doctor?.lastName}"
                        textDni.text = user.identification
                        textGender.text = "Masculino"
                        val date= Formatter.getLocaleDate(user.doctor?.birthdate ?: "")
                        textAge.text = Formatter.formatLocalDate(date ?: Date())
                        textCellphone.text = user.doctor?.phone
                        textEmail.text = user.email
                    }

                }

            }
            is UIViewState.Loading -> {
                // TODO: Handle UI loading
            }
            is UIViewState.Error -> {
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}