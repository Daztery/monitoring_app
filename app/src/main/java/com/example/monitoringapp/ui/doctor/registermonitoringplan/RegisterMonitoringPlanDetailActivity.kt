package com.example.monitoringapp.ui.doctor.registermonitoringplan

import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.Emergency
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.PlanRequest
import com.example.monitoringapp.databinding.ActivityRegisterMonitoringPlanDetailBinding
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterMonitoringPlanDetailActivity : AppCompatActivity() {

    private val registerMonitoringPlanViewModel: RegisterMonitoringPlanViewModel by viewModels()

    private lateinit var binding: ActivityRegisterMonitoringPlanDetailBinding

    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var startDate = 1641016800000
    private var endDate = 1704002400000
    var positionSpinner = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterMonitoringPlanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Registrar Plan de Monitoreo"

        setupObservers()

        val user = intent.getSerializableExtra(Constants.KEY_USER) as User

        registerMonitoringPlanViewModel.getEmergency()

        binding.run {
            editFullname.setText(user.patient?.getFullName())

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
            spinnerKindEmergency.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        positionSpinner = position+1
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

            buttonRegister.setOnClickListener {
                if (editFullname.text.isNotEmpty()) {
                    val planRequest = PlanRequest()
                    planRequest.code = (100..10000).random()
                    planRequest.emergencyTypeId = positionSpinner
                    planRequest.priorityTypeId = 1
                    planRequest.patientId = user.id
                    planRequest.startDate = startDate.toString()
                    planRequest.endDate = endDate.toString()
                    registerMonitoringPlanViewModel.createPlan(planRequest)
                } else {
                    toast("Completar todos los campos")
                }
            }
        }

    }

    private fun setupObservers() {
        registerMonitoringPlanViewModel.uiViewGetEmergencyStateObservable.observe(
            this,
            getAllEmergencyObserver
        )

        registerMonitoringPlanViewModel.uiViewCreatePlanStateObservable.observe(
            this,
            createPlanObserver
        )
    }

    //Observers
    private val getAllEmergencyObserver = Observer<UIViewState<List<Emergency>>> {
        when (it) {
            is UIViewState.Success -> {
                val itemsObserver = it.result

                val list = mutableListOf<String>()

                for (item in itemsObserver) {
                    list.add(item.name!!)
                }

                val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, list)
                arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

                binding.run {
                    //progressBar.gone()
                    spinnerKindEmergency.adapter = arrayAdapter
                }
            }
            is UIViewState.Loading -> {
                hideKeyboard()
                //binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                //binding.progressBar.visible()
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    private val createPlanObserver = Observer<UIViewState<Plan>> {
        when (it) {
            is UIViewState.Success -> {
                val itemObserver = it.result

                binding.run {
                    //progressBar.gone()
                    val intent = Intent(applicationContext, RegisterPrescriptionActivity::class.java)
                    intent.putExtra(Constants.KEY_PLAN, itemObserver)
                    startActivity(intent)
                    finish()
                }
            }
            is UIViewState.Loading -> {
                hideKeyboard()
                //binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                //binding.progressBar.visible()
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
        datePickerDialog?.datePicker?.minDate = System.currentTimeMillis()
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
                c.set(mYear, mMonth, mDay, 0, 0, 0)
                c.set(Calendar.MILLISECOND, 0)
                val date = Date(c.timeInMillis)
                val textCalendar = Formatter.formatLocalDate(date)
                currentDate = date
                binding.textEndDate.text = textCalendar

                endDate = c.timeInMillis
            }, mYear, mMonth, mDay
        )
        datePickerDialog?.datePicker?.minDate = System.currentTimeMillis()
        datePickerDialog?.show()
    }

}