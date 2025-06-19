package com.example.monitoringapp.ui.patient.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.FragmentProfileBinding
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.ui.information.InformationActivity
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

    private lateinit var user: User

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

        (activity as HomePatientActivity).title = "Perfil"

        binding.run {
            imageEdit.setOnClickListener {
                val intent = Intent(context, InformationActivity::class.java)
                intent.putExtra(Constants.KEY_USER, user)
                startActivity(intent)
            }
        }

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
                val userObserver = it.result
                user = userObserver
                binding.run {
                    progressBar.gone()
                    constraint.visible()
                    if (PreferencesHelper.type == PATIENT) {
                        textFullname.text = userObserver.patient?.getFullName()
                        textDni.text = userObserver.identification
                        textGender.text = "Masculino"
                        val date = Formatter.getLocaleDate(userObserver.patient?.birthdate ?: "")
                        textAge.text = Formatter.formatLocalDate(date ?: Date())
                        textCellphone.text = userObserver.patient?.phone
                        textEmail.text = userObserver.email
                    } else {
                        textFullname.text = userObserver.doctor?.getFullName()
                        textDni.text = userObserver.identification
                        textGender.text = "Masculino"
                        val date = Formatter.getLocaleDate(userObserver.doctor?.birthdate ?: "")
                        textAge.text = Formatter.formatLocalDate(date ?: Date())
                        textCellphone.text = userObserver.doctor?.phone
                        textEmail.text = userObserver.email
                    }
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
                toast(it.message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getSelf()
    }
}