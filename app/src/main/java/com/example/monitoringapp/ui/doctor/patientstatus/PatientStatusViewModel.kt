package com.example.monitoringapp.ui.doctor.patientstatus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.data.model.Status
import com.example.monitoringapp.usecase.report.GetPatientStatusUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PatientStatusViewModel @Inject constructor(
    private val getPatientStatusUseCase: GetPatientStatusUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetPatientStatusUIViewState =
        MutableLiveData<UIViewState<List<Status>>>()
    val uiViewGetPatientStatusStateObservable =
        _mutableGetPatientStatusUIViewState.asLiveData()

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

    private fun emitUIGetPatientStatusState(state: UIViewState<List<Status>>) {
        _mutableGetPatientStatusUIViewState.postValue(state)
    }

}