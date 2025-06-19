package com.example.monitoringapp.ui.doctor.prescription

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.model.Status
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.ActivityPatientPrescriptionBinding
import com.example.monitoringapp.ui.adapter.PrescriptionAdapter
import com.example.monitoringapp.ui.adapter.PrescriptionPatientAdapter
import com.example.monitoringapp.ui.patient.prescription.PrescriptionViewModel
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PatientPrescriptionActivity : AppCompatActivity() {

    private val prescriptionViewModel: PrescriptionViewModel by viewModels()

    private lateinit var binding: ActivityPatientPrescriptionBinding

    private lateinit var prescriptionAdapter: PrescriptionPatientAdapter

    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var datePickerDialog2: DatePickerDialog? = null
    private var startDate = 1646978400000
    private var endDate = 1672506000000
    private lateinit var user: User
    private var monitoringPlanId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Receta Médica"

        user = intent.getSerializableExtra(Constants.KEY_USER) as User
        Log.i("userdebug", user.toString())

        setupObservers()

        prescriptionViewModel.getPatientStatus(startDate.toString(), endDate.toString())

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
                prescriptionViewModel.getPatientStatus(
                    startDate.toString(),
                    endDate.toString()
                )
            }
        }
    }

    private fun setupObservers() {
        prescriptionViewModel.uiViewGetPatientPrescriptionStateObservable.observe(
            this,
            getPlanPrescriptionObserver
        )
        prescriptionViewModel.uiViewGetPatientStatusStateObservable.observe(
            this,
            getPatientStatusObserver
        )
    }

    //Observers
    private val getPlanPrescriptionObserver = Observer<UIViewState<Prescription>> {
        when (it) {
            is UIViewState.Success -> {
                val prescriptionObserver = it.result
                val list = mutableListOf<Prescription>()
                list.add(prescriptionObserver)
                binding.run {
                    prescriptionAdapter = PrescriptionPatientAdapter(list)
                    recycler.adapter = prescriptionAdapter
                }
            }
            is UIViewState.Error -> {
                toast(it.message)
            }
        }
    }

    private val getPatientStatusObserver = Observer<UIViewState<List<Status>>> {
        when (it) {
            is UIViewState.Success -> {
                val patientStatusObserver = it.result
                for (item in patientStatusObserver) {
                    if (user.id == item.userId) monitoringPlanId = item.monitoringPlanId!!
                }

                prescriptionViewModel.getPlanPrescription(monitoringPlanId)
            }
            is UIViewState.Error -> {
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
            this,
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
            this,
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
}