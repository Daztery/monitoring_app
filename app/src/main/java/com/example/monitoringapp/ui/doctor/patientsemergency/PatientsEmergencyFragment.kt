package com.example.monitoringapp.ui.doctor.patientsemergency

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.*
import com.example.monitoringapp.databinding.FragmentPatientStatusBinding
import com.example.monitoringapp.databinding.FragmentPatientsEmergencyBinding
import com.example.monitoringapp.ui.adapter.*
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.ui.doctor.home.HomeDoctorViewModel
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PatientsEmergencyFragment : Fragment() {

    private val patientsEmergencyViewModel: PatientsEmergencyViewModel by viewModels()

    private var _binding: FragmentPatientsEmergencyBinding? = null
    private val binding get() = _binding!!

    private lateinit var patientsByEmergencyAdapter: PatientsByEmergencyAdapter
    private lateinit var emergencyReportAdapter: EmergencyReportAdapter


    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var datePickerDialog2: DatePickerDialog? = null
    private var startDate = 1646978400000
    private var endDate = 1672506000000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientsEmergencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeDoctorActivity).title = "Paciente por tipo de emergencia"

        setupObservers()

        val user= PreferencesHelper.userData

        binding.run {

            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            recyclerEmergency.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            patientsEmergencyViewModel.getEmergencyReport(Date().time.toString())

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
                patientsEmergencyViewModel.getPatientsByEmergency(1, startDate.toString())
            }
        }
    }

    private fun setupObservers() {
        patientsEmergencyViewModel.uiViewGetPatientsByEmergencyStateObservable.observe(
            viewLifecycleOwner,
            getPatientsEmergencyObserver
        )
        patientsEmergencyViewModel.uiViewGetEmergencyReportStateObservable.observe(
            viewLifecycleOwner,
            getEmergencyReportObserver
        )
    }

    //Observers
    private val getPatientsEmergencyObserver = Observer<UIViewState<List<EmergencyType>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val prescriptionObserver = it.result
                binding.run {
                    patientsByEmergencyAdapter = PatientsByEmergencyAdapter(prescriptionObserver)
                    recycler.adapter = patientsByEmergencyAdapter
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

    private val getEmergencyReportObserver = Observer<UIViewState<List<Emergency>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val itemObserver = it.result
                binding.run {
                    emergencyReportAdapter = EmergencyReportAdapter(itemObserver)
                    recyclerEmergency.adapter = emergencyReportAdapter
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

        datePickerDialog2 = DatePickerDialog(
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
        datePickerDialog2?.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}