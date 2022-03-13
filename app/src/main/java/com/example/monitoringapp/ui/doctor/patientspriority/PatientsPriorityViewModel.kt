package com.example.monitoringapp.ui.doctor.patientspriority

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.usecase.report.GetPatientsByPriorityUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PatientsPriorityViewModel @Inject constructor(
    private val getPatientsByPriorityUseCase: GetPatientsByPriorityUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetPatientsByPriorityUIViewState =
        MutableLiveData<UIViewState<List<Report>>>()
    val uiViewGetPatientsByPriorityStateObservable =
        _mutableGetPatientsByPriorityUIViewState.asLiveData()

    fun getPatientsByPriority(
        PriorityId: Int,
        active: Boolean,
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
                       // emitUIGetPatientsByPriorityState(UIViewState.Success(data))
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

    private fun emitUIGetPatientsByPriorityState(state: UIViewState<List<Report>>) {
        _mutableGetPatientsByPriorityUIViewState.postValue(state)
    }

}