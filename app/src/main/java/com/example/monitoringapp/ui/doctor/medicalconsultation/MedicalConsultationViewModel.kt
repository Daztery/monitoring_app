package com.example.monitoringapp.ui.doctor.medicalconsultation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.usecase.monitoring.GetPatientHistoryUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MedicalConsultationViewModel @Inject constructor(
    private val getPatientHistoryUseCase: GetPatientHistoryUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetPatientHistoryUIViewState =
        MutableLiveData<UIViewState<List<Plan>>>()
    val uiViewGetPatientHistoryStateObservable =
        _mutableGetPatientHistoryUIViewState.asLiveData()

    fun getPatientHistory(
        patientId: Int
    ) {
        emitUIGetPatientHistoryState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPatientHistoryUseCase(patientId, false, false)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetPatientHistoryState(UIViewState.Success(data))
                    } else {
                        emitUIGetPatientHistoryState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetPatientHistoryState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetPatientHistoryState(state: UIViewState<List<Plan>>) {
        _mutableGetPatientHistoryUIViewState.postValue(state)
    }

}