package com.example.monitoringapp.ui.doctor.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.FragmentHomeDoctorBinding
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.ui.information.InformationActivity
import com.example.monitoringapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDoctorFragment : Fragment() {

    private val homeDoctorViewModel: HomeDoctorViewModel by viewModels()

    private var _binding: FragmentHomeDoctorBinding? = null
    private val binding get() = _binding!!

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeDoctorActivity).title = "Inicio"

        setupObservers()

        homeDoctorViewModel.getSelf()

    }

    private fun setupObservers() {
        homeDoctorViewModel.uiViewGetSelfStateObservable.observe(
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
                    PreferencesHelper.medicalCenter = userObserver.doctor?.medicalCenter?.name
                    textFullname.text = userObserver.doctor?.getFullName()
                    textDni.text = userObserver.identification
                    textCellphone.text = userObserver.doctor?.phone
                    textEmail.text = userObserver.email
                    imageCall.setOnClickListener {
                        val dialIntent = Intent(Intent.ACTION_DIAL)
                        dialIntent.data = Uri.parse("tel:" + textCellphone.text.toString())
                        startActivity(dialIntent)
                    }
                    imageEdit.setOnClickListener {
                        val intent = Intent(context, InformationActivity::class.java)
                        intent.putExtra(Constants.KEY_USER, user)
                        startActivity(intent)
                    }
                    imageEmail.setOnClickListener {
                        toast("Número copiado")
                        context?.copyToClipboard(textEmail.text.toString())
                    }
                }
            }
            is UIViewState.Loading -> {
                binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                binding.progressBar.visible()
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        homeDoctorViewModel.getSelf()
    }

}