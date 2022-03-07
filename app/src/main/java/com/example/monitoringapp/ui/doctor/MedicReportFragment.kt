package com.example.monitoringapp.ui.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.monitoringapp.R
import com.example.monitoringapp.databinding.FragmentMedicReportBinding
import com.example.monitoringapp.databinding.FragmentReportsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicReportFragment : Fragment() {

    private var _binding: FragmentMedicReportBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}