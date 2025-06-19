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
import com.example.monitoringapp.data.model.Priority
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
    private var startDate = 1646978400000
    private var endDate = 1672506000000
    var positionEmergencySpinner = 1
    var positionPrioritySpinner = 1
    private lateinit var user: User
    val code = (100..10000).random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterMonitoringPlanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Registrar Plan de Monitoreo"

        setupObservers()

        user = intent.getSerializableExtra(Constants.KEY_USER) as User

        registerMonitoringPlanViewModel.getAllEmergencies()
        registerMonitoringPlanViewModel.getAllPriorities()

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
                        positionEmergencySpinner = position + 1
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

            spinnerPriority.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        positionPrioritySpinner = position + 1
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }


            buttonRegister.setOnClickListener {
                if (editFullname.text.isNotEmpty()) {
                    val planRequest = PlanRequest()
                    planRequest.code = code
                    planRequest.emergencyTypeId = positionEmergencySpinner
                    planRequest.priorityTypeId = positionPrioritySpinner
                    planRequest.patientId = user.id
                    planRequest.startDate = startDate
                    planRequest.endDate = endDate
                    registerMonitoringPlanViewModel.createPlan(planRequest)
                } else {
                    toast("Completar todos los campos")
                }
            }
        }

    }

    private fun setupObservers() {
        registerMonitoringPlanViewModel.uiViewGetAllEmergenciesStateObservable.observe(
            this,
            getAllEmergenciesObserver
        )
        registerMonitoringPlanViewModel.uiViewGetAllPrioritiesStateObservable.observe(
            this,
            getAllPrioritiesObserver
        )

        registerMonitoringPlanViewModel.uiViewCreatePlanStateObservable.observe(
            this,
            createPlanObserver
        )
    }

    //Observers
    private val getAllEmergenciesObserver = Observer<UIViewState<List<Emergency>>> {
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
                toast(it.message)
            }
        }
    }

    private val getAllPrioritiesObserver = Observer<UIViewState<List<Priority>>> {
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
                    spinnerPriority.adapter = arrayAdapter
                }
            }
            is UIViewState.Loading -> {
                hideKeyboard()
                //binding.progressBar.visible()
            }
            is UIViewState.Error -> {
                //binding.progressBar.visible()
                toast(it.message)
            }
        }
    }

    private val createPlanObserver = Observer<UIViewState<Plan>> {
        when (it) {
            is UIViewState.Success -> {
                var itemObserver = it.result

                binding.run {
                    //progressBar.gone()
                    val intent =
                        Intent(applicationContext, RegisterPrescriptionActivity::class.java)
                    itemObserver.code = code
                    intent.putExtra(Constants.KEY_PLAN, itemObserver)
                    intent.putExtra(Constants.KEY_USER, user)
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
                //c.set(mYear, mMonth, mDay, 0, 0, 0)
                //c.set(Calendar.MILLISECOND, 0)
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
                c.set(mYear, mMonth, mDay, 23, 59, 59)
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