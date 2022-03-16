package com.example.monitoringapp.ui.doctor.prescription

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.databinding.ActivityMedicalConsultationBinding
import com.example.monitoringapp.databinding.ActivityPatientPrescriptionBinding
import com.example.monitoringapp.ui.adapter.PatientStatusAdapter
import com.example.monitoringapp.ui.adapter.PrescriptionAdapter
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.ui.patient.HomePatientActivity
import com.example.monitoringapp.ui.patient.prescription.PrescriptionViewModel
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PatientPrescriptionActivity : AppCompatActivity() {

    private val prescriptionViewModel: PrescriptionViewModel by viewModels()

    private lateinit var binding: ActivityPatientPrescriptionBinding

    private lateinit var prescriptionAdapter: PrescriptionAdapter

    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var datePickerDialog2: DatePickerDialog? = null
    private var startDate = 1646978400000
    private var endDate = 1672506000000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Receta Médica"


        setupObservers()
        prescriptionViewModel.getSelfPrescriptions(startDate.toString(), currentDate.toString())

        binding.run {

            recycler.layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

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
                prescriptionViewModel.getSelfPrescriptions(
                    startDate.toString(),
                    endDate.toString()
                )
            }
        }
    }

    private fun setupObservers() {
        prescriptionViewModel.uiViewGetSelfPrescriptionStateObservable.observe(
            this,
            getSelfPrescriptionObserver
        )
    }

    //Observers
    private val getSelfPrescriptionObserver = Observer<UIViewState<List<Prescription>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val prescriptionObserver = it.result
                binding.run {
                    prescriptionAdapter = PrescriptionAdapter(prescriptionObserver)
                    recycler.adapter = prescriptionAdapter
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
            this,
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
            this,
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
}