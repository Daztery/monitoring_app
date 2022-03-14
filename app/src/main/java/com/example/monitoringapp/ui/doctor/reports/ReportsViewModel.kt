package com.example.monitoringapp.ui.doctor.reports

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.usecase.prioritytype.GetPriorityUseCase
import com.example.monitoringapp.usecase.report.GetEmergencyReportUseCase
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

    fun getEmergencyReport(
        from: String
    ) {
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getEmergencyReportUseCase( false, from)
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
                getPriorityReportUseCase( false, from)
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

    private fun emitUIGetEmergencyReportState(state: UIViewState<List<Report>>) {
        _mutableGetEmergencyReportUIViewState.postValue(state)
    }

    private fun emitUIGetPriorityReportState(state: UIViewState<List<Report>>) {
        _mutableGetPriorityReportUIViewState.postValue(state)
    }

}