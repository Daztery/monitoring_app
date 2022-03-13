package com.example.monitoringapp.ui.doctor.patientsemergency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.usecase.report.GetPatientsByEmergencyUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PatientsEmergencyViewModel @Inject constructor(
    private val getPatientsByEmergencyUseCase: GetPatientsByEmergencyUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetPatientsByEmergencyUIViewState =
        MutableLiveData<UIViewState<List<Report>>>()
    val uiViewGetPatientsByEmergencyStateObservable =
        _mutableGetPatientsByEmergencyUIViewState.asLiveData()

    fun getPatientsByEmergency(
        emergencyId: Int,
        active: Boolean,
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
                       // emitUIGetPatientsByEmergencyState(UIViewState.Success(data))
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

    private fun emitUIGetPatientsByEmergencyState(state: UIViewState<List<Report>>) {
        _mutableGetPatientsByEmergencyUIViewState.postValue(state)
    }

}