package com.example.monitoringapp.ui.doctor.patientstatus

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.data.model.Status
import com.example.monitoringapp.databinding.FragmentPatientStatusBinding
import com.example.monitoringapp.ui.adapter.PatientStatusAdapter
import com.example.monitoringapp.ui.adapter.PrescriptionAdapter
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.ui.patient.prescription.PrescriptionViewModel
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PatientStatusFragment : Fragment() {

    private val patientStatusViewModel: PatientStatusViewModel by viewModels()

    private var _binding: FragmentPatientStatusBinding? = null
    private val binding get() = _binding!!

    private lateinit var patientStatusAdapter: PatientStatusAdapter

    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var startDate = 1641016800000
    private var endDate = 1704002400000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeDoctorActivity).title = "Estado de Pacientes"

        setupObservers()

        binding.run {

            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            textStartDate.setOnClickListener {
                showDatePickerDialogStartDate()
            }

            textEndDate.setOnClickListener {
                showDatePickerDialogEndDate()
            }

            imageStartDate.setOnClickListener {
                showDatePickerDialogStartDate()
            }

            imageEndDate.setOnClickListener {
                showDatePickerDialogEndDate()
            }

            buttonSearch.setOnClickListener {
                patientStatusViewModel.getPatientStatus(startDate.toString(),endDate.toString())
            }
        }

    }

    private fun setupObservers() {
        patientStatusViewModel.uiViewGetPatientStatusStateObservable.observe(
            viewLifecycleOwner,
            getPatientStatusObserver
        )
    }

    //Observers
    private val getPatientStatusObserver = Observer<UIViewState<List<Status>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val patientStatusObserver = it.result
                binding.run {
                    patientStatusAdapter = PatientStatusAdapter(patientStatusObserver)
                    recycler.adapter = patientStatusAdapter
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
    private fun showDatePickerDialogStartDate() {
        val c: Calendar = Calendar.getInstance()
        c.time = currentDate
        var mYear: Int = c.get(Calendar.YEAR)
        var mMonth: Int = c.get(Calendar.MONTH)
        var mDay: Int = c.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                mYear = year
                mMonth = monthOfYear
                mDay = dayOfMonth
                c.set(mYear, mMonth, mDay, 0, 0, 0)
                c.set(Calendar.MILLISECOND, 0)
                val date = Date(c.timeInMillis)
                val textCalendar = Formatter.formatLocalDate(date)
                currentDate = date
                binding.textStartDate.text = textCalendar

                startDate = c.timeInMillis

            }, mYear, mMonth, mDay
        )
        datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis()
        datePickerDialog?.show()
    }

    private fun showDatePickerDialogEndDate() {
        val c: Calendar = Calendar.getInstance()
        c.time = currentDate
        var mYear: Int = c.get(Calendar.YEAR)
        var mMonth: Int = c.get(Calendar.MONTH)
        var mDay: Int = c.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                mYear = year
                mMonth = monthOfYear
                mDay = dayOfMonth
                c.set(mYear, mMonth, mDay, 0, 0, 0)
                c.set(Calendar.MILLISECOND, 0)
                val date = Date(c.timeInMillis)
                val textCalendar = Formatter.formatLocalDate(date)
                currentDate = date
                binding.textEndDate.text = textCalendar

                endDate = c.timeInMillis
            }, mYear, mMonth, mDay
        )
        datePickerDialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}