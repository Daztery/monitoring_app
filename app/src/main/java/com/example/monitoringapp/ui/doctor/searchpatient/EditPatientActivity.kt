package com.example.monitoringapp.ui.doctor.searchpatient

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.Patient
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.UpdatePatientRequest
import com.example.monitoringapp.databinding.ActivityEditPatientBinding
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditPatientActivity : AppCompatActivity() {

    private val editPatientViewModel: EditPatientViewModel by viewModels()

    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null

    private lateinit var binding: ActivityEditPatientBinding

    private lateinit var patient: Patient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Editar paciente"

        setupObservers()

        val user = intent.getSerializableExtra("update") as User


        if (user.identification != "") {

            editPatientViewModel.getPatient(user.identification?.toInt() ?: 0 )

            patient = user.patient!!

            binding.run {

                buttonBirthday.setOnClickListener {
                    showDatePickerDialog()
                }

                buttonUpdate.setOnClickListener {
                    patient.firstName = editName.text.toString()
                    patient.lastName = editLastname.text.toString()
                    patient.birthdate = editPhone.text.toString()
                    patient.phone = editPhone.text.toString()
                    patient.height = editHeight.text.toString().toInt()
                    patient.weight = editWeight.text.toString().toInt()
                    val updatePatientRequest = UpdatePatientRequest()
                    updatePatientRequest.email = user.email
                    updatePatientRequest.patient = patient
                    editPatientViewModel.updatePatient(user.id ?: 0, updatePatientRequest)
                }
            }
        }

    }

    private fun setupObservers() {
        editPatientViewModel.uiViewUpdatePatientStateObservable.observe(
            this@EditPatientActivity,
            updatePatientObserver
        )
        editPatientViewModel.uiViewGetPatientStateObservable.observe(
            this@EditPatientActivity,
            getPatientObserver
        )
    }

    //Observers
    private val updatePatientObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                binding.run {
                    //progressBar.gone()
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

    private val getPatientObserver = Observer<UIViewState<User>> {
        when (it) {
            is UIViewState.Success -> {
                val userObserver = it.result
                binding.run {
                    //progressBar.gone()
                    editName.setText(userObserver.patient?.firstName)
                    editLastname.setText(userObserver.patient?.lastName)
                    editDni.setText(userObserver.identification)
                    editMail.setText(userObserver.email)
                    editPhone.setText(userObserver.patient?.phone)
                    editHeight.setText(userObserver.patient?.height.toString())
                    editWeight.setText(userObserver.patient?.weight.toString())
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

    private fun showDatePickerDialog() {
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
                binding.buttonBirthday.text = textCalendar

                patient.birthdate = c.timeInMillis.toString()
            }, mYear, mMonth, mDay
        )
        datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis()
        datePickerDialog?.show()
    }

}