package com.example.monitoringapp.ui.doctor.medicalconsultation

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.ActivityMedicalConsultationBinding
import com.example.monitoringapp.ui.adapter.PatientHistoryAdapter
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MedicalConsultationActivity : AppCompatActivity() {

    private val medicalConsultationViewModel: MedicalConsultationViewModel by viewModels()

    private lateinit var binding: ActivityMedicalConsultationBinding

    private lateinit var patientHistoryAdapter: PatientHistoryAdapter

    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var startDate = 1646978400000
    private var endDate = 1672506000000
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalConsultationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Consultas Médicas"

        user = intent.getSerializableExtra(Constants.KEY_USER) as User

        setupObservers()
        binding.run {

            recycler.layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            textStartDate.setOnClickListener {
                showDatePickerDialogStartDate()
            }

            textEndDate.setOnClickListener {
                showDatePickerDialogEndDate()
            }

            buttonSearch.setOnClickListener {
                medicalConsultationViewModel.getPatientHistory(user.id!!)
            }
        }

    }

    private fun setupObservers() {
        medicalConsultationViewModel.uiViewGetPatientHistoryStateObservable.observe(
            this,
            getPatientHistoryObserver
        )
    }

    //Observers
    private val getPatientHistoryObserver = Observer<UIViewState<List<Plan>>> {
        when (it) {
            is UIViewState.Success -> {
                //binding.progressBar.gone()
                val itemObserver = it.result
                binding.run {
                    patientHistoryAdapter = PatientHistoryAdapter(
                        itemObserver,
                        onClickCallback = { plan -> onClickCallback(plan) })
                    recycler.adapter = patientHistoryAdapter
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
                binding.textEndDate.text = textCalendar

                endDate = c.timeInMillis
            }, mYear, mMonth, mDay
        )
        datePickerDialog?.show()
    }

    // Callbacks
    private fun onClickCallback(plan: Plan) {
        val intent = Intent(this, MedicalConsultationDetailActivity::class.java)
        intent.putExtra(Constants.KEY_PLAN, plan)
        intent.putExtra(Constants.KEY_USER, user)
        startActivity(intent)
    }

}