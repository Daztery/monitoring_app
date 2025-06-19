package com.example.monitoringapp.ui.patient.dailyreport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.TemperatureSaturation
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.DailyReportRequest
import com.example.monitoringapp.data.network.request.UpdatePatientRequest
import com.example.monitoringapp.data.network.response.UpdateResponse
import com.example.monitoringapp.usecase.monitoring.GetSelfPlansUseCase
import com.example.monitoringapp.usecase.monitoring.dailyreport.CreateDailyReportUseCase
import com.example.monitoringapp.usecase.user.GetSelfUseCase
import com.example.monitoringapp.usecase.user.UpdatePatientUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DailyReportViewModel @Inject constructor(
    private val getSelfPlansUseCase: GetSelfPlansUseCase,
    private val createDailyReportUseCase: CreateDailyReportUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableCreateReportUIViewState =
        MutableLiveData<UIViewState<TemperatureSaturation>>()
    val uiViewCreateReportStateObservable = _mutableCreateReportUIViewState.asLiveData()

    private val _mutableGetSelfPlansUIViewState =
        MutableLiveData<UIViewState<Plan>>()
    val uiViewGetSelfPlansStateObservable =
        _mutableGetSelfPlansUIViewState.asLiveData()

    fun createReport(planId: Int, dailyReportRequest: DailyReportRequest) {
        emitUICreateReportState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                createDailyReportUseCase(planId, dailyReportRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUICreateReportState(UIViewState.Success(data))
                    } else {
                        emitUICreateReportState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUICreateReportState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getSelfPlans() {
        emitUIGetSelfPlansState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getSelfPlansUseCase(true)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data?.get(result.data.data.size - 1)
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

    private fun emitUICreateReportState(state: UIViewState<TemperatureSaturation>) {
        _mutableCreateReportUIViewState.postValue(state)
    }

    private fun emitUIGetSelfPlansState(state: UIViewState<Plan>) {
        _mutableGetSelfPlansUIViewState.postValue(state)
    }

}