package com.example.monitoringapp.ui.doctor.patientsemergency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Emergency
import com.example.monitoringapp.data.model.EmergencyType
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.usecase.emergencytype.GetAllEmergencyUseCase
import com.example.monitoringapp.usecase.report.GetPatientsByEmergencyUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PatientsEmergencyViewModel @Inject constructor(
    private val getPatientsByEmergencyUseCase: GetPatientsByEmergencyUseCase,
    private val getEmergencyReportUseCase: GetAllEmergencyUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetPatientsByEmergencyUIViewState =
        MutableLiveData<UIViewState<List<EmergencyType>>>()
    val uiViewGetPatientsByEmergencyStateObservable =
        _mutableGetPatientsByEmergencyUIViewState.asLiveData()

    private val _mutableGetEmergencyReportUIViewState =
        MutableLiveData<UIViewState<List<Emergency>>>()
    val uiViewGetEmergencyReportStateObservable =
        _mutableGetEmergencyReportUIViewState.asLiveData()

    fun getPatientsByEmergency(
        emergencyId: Int,
        from: String
    ) {
        emitUIGetPatientsByEmergencyState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPatientsByEmergencyUseCase(emergencyId, false, from)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetPatientsByEmergencyState(UIViewState.Success(data))
                    } else {
                        emitUIGetPatientsByEmergencyState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetPatientsByEmergencyState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getEmergencyReport(
        from: String
    ) {
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getEmergencyReportUseCase()
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

    private fun emitUIGetPatientsByEmergencyState(state: UIViewState<List<EmergencyType>>) {
        _mutableGetPatientsByEmergencyUIViewState.postValue(state)
    }

    private fun emitUIGetEmergencyReportState(state: UIViewState<List<Emergency>>) {
        _mutableGetEmergencyReportUIViewState.postValue(state)
    }


}