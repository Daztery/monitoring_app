package com.example.monitoringapp.ui.doctor.registerpatient

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.network.request.PatientRequest
import com.example.monitoringapp.data.network.request.RegisterPatientRequest
import com.example.monitoringapp.data.network.response.RegisterPatientResponse
import com.example.monitoringapp.databinding.FragmentRegisterPatientBinding
import com.example.monitoringapp.ui.doctor.HomeDoctorActivity
import com.example.monitoringapp.util.*
import com.example.monitoringapp.util.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterPatientFragment : Fragment() {

    private val registerPatientViewModel: RegisterPatientViewModel by viewModels()

    private var _binding: FragmentRegisterPatientBinding? = null
    private val binding get() = _binding!!

    var currentDate: Date = DataUtil.getCurrentDate()
    private var datePickerDialog: DatePickerDialog? = null
    private var birthday = 1641016800000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterPatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeDoctorActivity).title = "Registrar Paciente"
        setupObservers()

        binding.run {

            textMedicalCenter.text = PreferencesHelper.medicalCenter

            textBirthday.setOnClickListener {
                showDatePickerDialogBirthday()
            }

            imageBirthday.setOnClickListener {
                showDatePickerDialogBirthday()
            }

            buttonRegister.setOnClickListener {
                if (editWeight.text.isNotEmpty() &&
                    editBlood.text.isNotEmpty() &&
                    editDni.text.isNotEmpty() &&
                    editHeight.text.isNotEmpty() &&
                    editLastname.text.isNotEmpty() &&
                    editName.text.isNotEmpty() &&
                    editMail.text.isNotEmpty() &&
                    editPhone.text.isNotEmpty()
                ) {
                    val registerPatientRequest = RegisterPatientRequest()
                    registerPatientRequest.email = editMail.text.toString()
                    registerPatientRequest.idType = "DNI"
                    registerPatientRequest.identification = editDni.text.toString()
                    registerPatientRequest.password = editPassword.text.toString()
                    val patientRequest = PatientRequest()
                    patientRequest.firstName = editName.text.toString()
                    patientRequest.lastName = editLastname.text.toString()
                    patientRequest.birthdate = birthday
                    patientRequest.phone = editPhone.text.toString()
                    patientRequest.height = editHeight.text.toString().toInt()
                    patientRequest.weight = editWeight.text.toString().toInt()
                    patientRequest.bloodType = editBlood.text.toString()
                    registerPatientRequest.patient = patientRequest
                    registerPatientViewModel.registerPatient(registerPatientRequest)
                } else {
                    toast("Completar todos los campos")
                }
            }
        }
    }

    private fun setupObservers() {
        registerPatientViewModel.uiViewRegisterPatientStateObservable.observe(
            viewLifecycleOwner,
            registerPatientObserver
        )
    }

    //Observers
    private val registerPatientObserver = Observer<UIViewState<RegisterPatientResponse>> {
        when (it) {
            is UIViewState.Success -> {
                binding.run {
                    editWeight.setText("")
                    editBlood.setText("")
                    editDni.setText("")
                    editHeight.setText("")
                    editLastname.setText("")
                    editName.setText("")
                    editMail.setText("")
                    editPhone.setText("")
                    editPassword.setText("")
                }
                toast("Paciente registrado")
            }
            is UIViewState.Error -> {
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    private fun showDatePickerDialogBirthday() {
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
                c.set(mYear, mMonth, mDay, 5, 0, 0)
                c.set(Calendar.MILLISECOND, 0)
                val date = Date(c.timeInMillis)
                val textCalendar = Formatter.formatLocalDate(date)
                currentDate = date
                binding.textBirthday.text = textCalendar

                birthday = c.timeInMillis

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