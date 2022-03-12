package com.example.monitoringapp.ui.auth

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.RecoverPasswordRequest
import com.example.monitoringapp.databinding.ActivityForgotPasswordBinding
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {

    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    private lateinit var binding: ActivityForgotPasswordBinding

    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var dateMillisecond = 1641016800000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()

        binding.run {

            textStartDate.setOnClickListener {
                showDatePickerDialogDate()
            }

            imageStartDate.setOnClickListener {
                showDatePickerDialogDate()
            }

            buttonNext.setOnClickListener {
                if (editDni.text.isNullOrEmpty()) {
                    toast("Completar el campo de DNI")
                } else {
                    val recoverPasswordRequest = RecoverPasswordRequest()
                    recoverPasswordRequest.birthdate = dateMillisecond
                    recoverPasswordRequest.identification = editDni.text.toString()
                    authenticationViewModel.recoverPassword(recoverPasswordRequest)
                }
            }
        }
    }

    private fun setupObservers() {
        authenticationViewModel.uiViewRecoverPasswordStateObservable.observe(
            this@ForgotPasswordActivity,
            recoverPasswordObserver
        )
    }

    //Observer
    private val recoverPasswordObserver = Observer<UIViewState<String>> {
        when (it) {
            is UIViewState.Success -> {
                val message = it.result
                showAlertDialog(message)
            }
            is UIViewState.Loading -> {
                // TODO: Handle UI loading
            }
            is UIViewState.Error -> {
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }


    private fun showAlertDialog(title: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)

        builder.setNeutralButton("Ok") { _, _ ->
            val intent = Intent(this@ForgotPasswordActivity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun showDatePickerDialogDate() {
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
                dateMillisecond = c.timeInMillis
            }, mYear, mMonth, mDay
        )
        datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis()
        datePickerDialog?.show()
    }

}