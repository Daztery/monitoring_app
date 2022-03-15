package com.example.monitoringapp.ui.doctor.medicreport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.Report
import com.example.monitoringapp.usecase.monitoring.GetSelfPlansUseCase
import com.example.monitoringapp.usecase.report.GetEmergencyReportUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MedicRecordViewModel @Inject constructor(
    private val getSelfPlansUseCase: GetSelfPlansUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetSelfPlansUIViewState =
        MutableLiveData<UIViewState<List<Plan>>>()
    val uiViewGetSelfPlansStateObservable =
        _mutableGetSelfPlansUIViewState.asLiveData()

    fun getSelfPlans() {
        emitUIGetSelfPlansState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getSelfPlansUseCase(false)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetSelfPlansState(UIViewState.Success(data))
                    } else {
                        emitUIGetSelfPlansState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetSelfPlansState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetSelfPlansState(state: UIViewState<List<Plan>>) {
        _mutableGetSelfPlansUIViewState.postValue(state)
    }

}