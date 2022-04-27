package com.example.monitoringapp.ui.doctor.medicalmonitoring

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.*
import com.example.monitoringapp.data.network.request.DailyReportDateRequest
import com.example.monitoringapp.usecase.monitoring.GetSelfPlansUseCase
import com.example.monitoringapp.usecase.monitoring.dailyreport.GetDateUseCase
import com.example.monitoringapp.usecase.monitoring.dailyreport.GetFromPatientUseCase
import com.example.monitoringapp.usecase.report.GetPatientStatusUseCase
import com.example.monitoringapp.usecase.user.GetSelfUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MedicalMonitoringViewModel @Inject constructor(
    private val getFromPatientUseCase: GetFromPatientUseCase,
    private val getPatientStatusUseCase: GetPatientStatusUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetFromPatientUIViewState =
        MutableLiveData<UIViewState<TemperatureSaturation>>()
    val uiViewGetFromPatientStateObservable =
        _mutableGetFromPatientUIViewState.asLiveData()

    private val _mutableGetPatientStatusUIViewState =
        MutableLiveData<UIViewState<List<Status>>>()
    val uiViewGetPatientStatusStateObservable =
        _mutableGetPatientStatusUIViewState.asLiveData()

    fun getReportFromPatient(
        planId: Int,
        patientId: Int,
        dailyReportDateRequest: DailyReportDateRequest
    ) {
        emitUIGetReportFromPatientState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getFromPatientUseCase(planId, patientId, dailyReportDateRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetReportFromPatientState(UIViewState.Success(data))
                    } else {
                        emitUIGetReportFromPatientState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetReportFromPatientState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getPatientStatus(
        from: String,
        to: String
    ) {
        emitUIGetPatientStatusState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPatientStatusUseCase(true, from, to)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetPatientStatusState(UIViewState.Success(data))
                    } else {
                        emitUIGetPatientStatusState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetPatientStatusState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetReportFromPatientState(state: UIViewState<TemperatureSaturation>) {
        _mutableGetFromPatientUIViewState.postValue(state)
    }

    private fun emitUIGetPatientStatusState(state: UIViewState<List<Status>>) {
        _mutableGetPatientStatusUIViewState.postValue(state)
    }

}