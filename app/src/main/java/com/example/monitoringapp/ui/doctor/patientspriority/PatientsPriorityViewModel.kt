package com.example.monitoringapp.ui.doctor.patientspriority

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Priority
import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.usecase.prioritytype.GetAllPriorityUseCase
import com.example.monitoringapp.usecase.report.GetPatientsByPriorityUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PatientsPriorityViewModel @Inject constructor(
    private val getPatientsByPriorityUseCase: GetPatientsByPriorityUseCase,
    private val getPriorityReportUseCase: GetAllPriorityUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetPatientsByPriorityUIViewState =
        MutableLiveData<UIViewState<List<PriorityType>>>()
    val uiViewGetPatientsByPriorityStateObservable =
        _mutableGetPatientsByPriorityUIViewState.asLiveData()

    private val _mutableGetPriorityReportUIViewState =
        MutableLiveData<UIViewState<List<Priority>>>()
    val uiViewGetPriorityReportStateObservable =
        _mutableGetPriorityReportUIViewState.asLiveData()

    fun getPatientsByPriority(
        PriorityId: Int,
        from: String
    ) {
        emitUIGetPatientsByPriorityState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPatientsByPriorityUseCase(PriorityId, false, from)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetPatientsByPriorityState(UIViewState.Success(data))
                    } else {
                        emitUIGetPatientsByPriorityState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetPatientsByPriorityState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getPriorityReport(
        from: String
    ) {
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPriorityReportUseCase()
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

    private fun emitUIGetPatientsByPriorityState(state: UIViewState<List<PriorityType>>) {
        _mutableGetPatientsByPriorityUIViewState.postValue(state)
    }

    private fun emitUIGetPriorityReportState(state: UIViewState<List<Priority>>) {
        _mutableGetPriorityReportUIViewState.postValue(state)
    }

}