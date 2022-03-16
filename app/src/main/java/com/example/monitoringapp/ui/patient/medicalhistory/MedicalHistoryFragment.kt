package com.example.monitoringapp.ui.patient.medicalhistory

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.FragmentMedicalHistoryBinding
import com.example.monitoringapp.ui.adapter.PlanAdapter
import com.example.monitoringapp.ui.adapter.PrescriptionAdapter
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.ui.patient.medicalrecord.MedicalRecordViewModel
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MedicalHistoryFragment : Fragment() {

    private val medicalHistoryViewModel: MedicalHistoryViewModel by viewModels()
    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null

    private var _binding: FragmentMedicalHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var planAdapter: PlanAdapter
    private var startDate = 1646978400000
    private var endDate = 1672506000000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicalHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomePatientActivity).title = "Historial Médico"

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

            buttonSearch.setOnClickListener {
                medicalHistoryViewModel.getSelfPlan()
            }
        }
    }

    private fun setupObservers() {
        medicalHistoryViewModel.uiViewGetSelfPrescriptionStateObservable.observe(
            viewLifecycleOwner,
            getSelfPlanObserver
        )
    }

    //Observers
    private val getSelfPlanObserver = Observer<UIViewState<List<Plan>>> {
        when (it) {
            is UIViewState.Success -> {
                val planObserver = it.result
                binding.run {
                    progressBar.gone()
                    recycler.visible()
                    planAdapter = PlanAdapter(planObserver)
                    recycler.adapter = planAdapter
                }
            }
            is UIViewState.Loading -> {
                binding.run {
                    progressBar.visible()
                    recycler.gone()
                }
            }
            is UIViewState.Error -> {
                binding.run {
                    progressBar.visible()
                    recycler.gone()
                }
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
        datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis()
        datePickerDialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}