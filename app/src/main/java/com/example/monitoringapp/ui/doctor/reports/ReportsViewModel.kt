package com.example.monitoringapp.ui.doctor.reports

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.*
import com.example.monitoringapp.usecase.emergencytype.GetAllEmergencyUseCase
import com.example.monitoringapp.usecase.prioritytype.GetAllPriorityUseCase
import com.example.monitoringapp.usecase.prioritytype.GetPriorityUseCase
import com.example.monitoringapp.usecase.report.GetEmergencyReportUseCase
import com.example.monitoringapp.usecase.report.GetPatientStatusResumeUseCase
import com.example.monitoringapp.usecase.report.GetPatientsByPriorityUseCase
import com.example.monitoringapp.usecase.report.GetPriorityReportUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val getEmergencyReportUseCase: GetEmergencyReportUseCase,
    private val getPriorityReportUseCase: GetPriorityReportUseCase,
    private val getPatientStatusResumeUseCase: GetPatientStatusResumeUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetEmergencyReportUIViewState =
        MutableLiveData<UIViewState<List<Report>>>()
    val uiViewGetEmergencyReportStateObservable =
        _mutableGetEmergencyReportUIViewState.asLiveData()

    private val _mutableGetPriorityReportUIViewState =
        MutableLiveData<UIViewState<List<Report>>>()
    val uiViewGetPriorityReportStateObservable =
        _mutableGetPriorityReportUIViewState.asLiveData()

    private val _mutableGetPatientStatusResumeUIViewState =
        MutableLiveData<UIViewState<List<ReportStatus>>>()
    val uiViewGetPatientStatusResumeStateObservable =
        _mutableGetPatientStatusResumeUIViewState.asLiveData()

    fun getEmergencyReport(
        from: String
    ) {
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getEmergencyReportUseCase(from)
            }
            emitUIGetEmergencyReportState(UIViewState.Loading)
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetEmergencyReportState(UIViewState.Success(data))
                    } else {
                        emitUIGetEmergencyReportState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetEmergencyReportState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getPriorityReport(
        from: String
    ) {
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPriorityReportUseCase(from)
            }
            emitUIGetPriorityReportState(UIViewState.Loading)
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetPriorityReportState(UIViewState.Success(data))
                    } else {
                        emitUIGetPriorityReportState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetPriorityReportState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getPatientStatusResume(
        from: String
    ) {
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPatientStatusResumeUseCase( true, from)
            }
            emitUIGetPatientStatusResumeState(UIViewState.Loading)
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetPatientStatusResumeState(UIViewState.Success(data))
                    } else {
                        emitUIGetPatientStatusResumeState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetPatientStatusResumeState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetEmergencyReportState(state: UIViewState<List<Report>>) {
        _mutableGetEmergencyReportUIViewState.postValue(state)
    }

    private fun emitUIGetPriorityReportState(state: UIViewState<List<Report>>) {
        _mutableGetPriorityReportUIViewState.postValue(state)
    }

    private fun emitUIGetPatientStatusResumeState(state: UIViewState<List<ReportStatus>>) {
        _mutableGetPatientStatusResumeUIViewState.postValue(state)
    }

}