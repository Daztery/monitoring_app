package com.example.monitoringapp.ui.doctor.alerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Alert
import com.example.monitoringapp.usecase.alert.GetAlertsFromPatientUseCase
import com.example.monitoringapp.usecase.alert.GetAllAlertsUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlertsDoctorViewModel @Inject constructor(
    private val getAllAlertsUseCase: GetAllAlertsUseCase,
    private val getAlertsFromPatientUseCase: GetAlertsFromPatientUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetAllAlertsUIViewState = MutableLiveData<UIViewState<List<Alert>>>()
    val uiViewGetSelfAlertStateObservable = _mutableGetAllAlertsUIViewState.asLiveData()

    private val _mutableGetAlertsFromPatientUIViewState = MutableLiveData<UIViewState<List<Alert>>>()
    val uiViewGetAlertsFromPatientStateObservable = _mutableGetAlertsFromPatientUIViewState.asLiveData()

    fun getAllAlerts() {
        viewModelScope.launch {
            emitUIGetAllAlertsState(UIViewState.Loading)
            val result = withContext(dispatchers.io) {
                getAllAlertsUseCase()
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetAllAlertsState(UIViewState.Success(data))
                    } else {
                        emitUIGetAllAlertsState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetAllAlertsState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getAlertsFromPatient(patientId: Int) {
        viewModelScope.launch {
            emitUIGetAlertsFromPatientState(UIViewState.Loading)
            val result = withContext(dispatchers.io) {
                getAlertsFromPatientUseCase(patientId)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetAlertsFromPatientState(UIViewState.Success(data))
                    } else {
                        emitUIGetAlertsFromPatientState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetAlertsFromPatientState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetAllAlertsState(state: UIViewState<List<Alert>>) {
        _mutableGetAllAlertsUIViewState.postValue(state)
    }

    private fun emitUIGetAlertsFromPatientState(state: UIViewState<List<Alert>>) {
        _mutableGetAlertsFromPatientUIViewState.postValue(state)
    }

}