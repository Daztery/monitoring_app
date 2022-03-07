package com.example.monitoringapp.ui.doctor

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monitoringapp.databinding.ActivityEditPatientBinding
import com.example.monitoringapp.util.DataUtil
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditPatientActivity : AppCompatActivity() {

    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null

    private lateinit var binding: ActivityEditPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.run {
            buttonBirthday.setOnClickListener {
                showDatePickerDialog()
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
                c.set(mYear, mMonth, mDay)

                val date = Date(c.timeInMillis)
                val textCalendar = Formatter.formatLocalDate(date)
                currentDate = date
                binding.buttonBirthday.text = textCalendar

                //userBirthdate = c.timeInMillis / 1000
            }, mYear, mMonth, mDay
        )
        datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis()
        datePickerDialog?.show()
    }

}