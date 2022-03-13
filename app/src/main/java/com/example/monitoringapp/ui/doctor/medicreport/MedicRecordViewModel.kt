package com.example.monitoringapp.ui.doctor.medicreport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.usecase.report.GetEmergencyReportUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MedicRecordViewModel @Inject constructor(
    private val getEmergencyReportUseCase: GetEmergencyReportUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetEmergencyReportUIViewState =
        MutableLiveData<UIViewState<List<Report>>>()
    val uiViewGetEmergencyReportStateObservable =
        _mutableGetEmergencyReportUIViewState.asLiveData()

    fun getEmergencyReport(
        from: String
    ) {
        emitUIGetEmergencyReportState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getEmergencyReportUseCase(false, from)
            }
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

    private fun emitUIGetEmergencyReportState(state: UIViewState<List<Report>>) {
        _mutableGetEmergencyReportUIViewState.postValue(state)
    }

}