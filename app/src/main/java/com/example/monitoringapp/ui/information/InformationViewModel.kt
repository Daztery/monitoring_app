package com.example.monitoringapp.ui.patient.information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.UpdateDoctorRequest
import com.example.monitoringapp.data.network.request.UpdatePatientRequest
import com.example.monitoringapp.usecase.user.UpdateDoctorUseCase
import com.example.monitoringapp.usecase.user.UpdatePatientUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val updateDoctorUseCase: UpdateDoctorUseCase,
    private val updatePatientUseCase: UpdatePatientUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableUpdatePatientUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewUpdatePatientStateObservable = _mutableUpdatePatientUIViewState.asLiveData()

    private val _mutableUpdateDoctorUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewUpdateDoctorStateObservable = _mutableUpdateDoctorUIViewState.asLiveData()

    fun updatePatient(id: Int, updatePatientRequest: UpdatePatientRequest) {
        emitUIUpdatePatientState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                updatePatientUseCase(id, updatePatientRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.clearRes?.data
                    if (data != null) {
                        emitUIUpdatePatientState(UIViewState.Success(data))
                    } else {
                        emitUIUpdatePatientState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIUpdatePatientState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun updateDoctor(id: Int, updateDoctorRequest: UpdateDoctorRequest) {
        emitUIUpdateDoctorState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                updateDoctorUseCase(id, updateDoctorRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.clearRes?.data
                    if (data != null) {
                        emitUIUpdateDoctorState(UIViewState.Success(data))
                    } else {
                        emitUIUpdateDoctorState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIUpdateDoctorState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIUpdatePatientState(state: UIViewState<User>) {
        _mutableUpdatePatientUIViewState.postValue(state)
    }

    private fun emitUIUpdateDoctorState(state: UIViewState<User>) {
        _mutableUpdateDoctorUIViewState.postValue(state)
    }

}