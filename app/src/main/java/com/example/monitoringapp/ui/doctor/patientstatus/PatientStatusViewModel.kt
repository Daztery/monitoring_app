package com.example.monitoringapp.ui.doctor.patientstatus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.data.model.ReportStatus
import com.example.monitoringapp.data.model.Status
import com.example.monitoringapp.usecase.report.GetPatientStatusResumeUseCase
import com.example.monitoringapp.usecase.report.GetPatientStatusUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PatientStatusViewModel @Inject constructor(
    private val getPatientStatusUseCase: GetPatientStatusUseCase,
    private val getPatientStatusResumeUseCase: GetPatientStatusResumeUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetPatientStatusUIViewState =
        MutableLiveData<UIViewState<List<Status>>>()
    val uiViewGetPatientStatusStateObservable =
        _mutableGetPatientStatusUIViewState.asLiveData()

    private val _mutableGetPatientStatusResumeUIViewState =
        MutableLiveData<UIViewState<List<ReportStatus>>>()
    val uiViewGetPatientStatusResumeStateObservable =
        _mutableGetPatientStatusResumeUIViewState.asLiveData()

    fun getPatientStatus(
        from: String,
        to: String
    ) {
        emitUIGetPatientStatusState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPatientStatusUseCase(false, from, to)
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

    fun getPatientStatusResume(
        from: String
    ) {
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPatientStatusResumeUseCase( false, from)
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

    private fun emitUIGetPatientStatusState(state: UIViewState<List<Status>>) {
        _mutableGetPatientStatusUIViewState.postValue(state)
    }

    private fun emitUIGetPatientStatusResumeState(state: UIViewState<List<ReportStatus>>) {
        _mutableGetPatientStatusResumeUIViewState.postValue(state)
    }

}