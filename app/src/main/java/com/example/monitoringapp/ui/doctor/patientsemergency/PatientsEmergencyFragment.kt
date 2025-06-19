package com.example.monitoringapp.ui.doctor.patientsemergency

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.*
import com.example.monitoringapp.databinding.FragmentPatientsEmergencyBinding
import com.example.monitoringapp.ui.adapter.*
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
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
    private lateinit var reportAdapter: ReportAdapter


    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var datePickerDialog2: DatePickerDialog? = null
    private var startDate = 1646978400000
    private var endDate = 1672506000000
    val listIds = mutableListOf<Int>()

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

        val user = PreferencesHelper.userData

        binding.run {

            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            recyclerEmergency.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            patientsEmergencyViewModel.getEmergencyReport(startDate.toString())

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
                patientsEmergencyViewModel.getEmergencyReport(startDate.toString())
            }

            spinnerKindEmergency.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position == 0) return
                        patientsEmergencyViewModel.getPatientsByEmergency(
                            listIds[position+1],
                            startDate.toString()
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
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
                toast(it.message)
            }
        }
    }

    private val getEmergencyReportObserver = Observer<UIViewState<List<Report>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val itemObserver = it.result
                val listNames = mutableListOf<String>()

                listNames.add("Seleccionar tipo de emergencia")
                listIds.add(0)

                for (item in itemObserver) {
                    listNames.add(item.name!!)
                    listIds.add(item.id!!)
                }

                val arrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.simple_spinner_item, listNames)
                arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

                binding.run {
                    reportAdapter = ReportAdapter(itemObserver)
                    recyclerEmergency.adapter = reportAdapter
                    spinnerKindEmergency.adapter = arrayAdapter
                }
            }
            is UIViewState.Loading -> {
                //binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                //binding.progressBar.gone()
                toast(it.message)
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
                c.set(mYear, mMonth, mDay, 12, 0, 0)
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
                c.set(mYear, mMonth, mDay, 12, 0, 0)
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