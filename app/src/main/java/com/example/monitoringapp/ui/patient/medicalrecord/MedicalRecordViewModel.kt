package com.example.monitoringapp.ui.patient.medicalrecord

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.TemperatureSaturation
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.DailyReportDateRequest
import com.example.monitoringapp.usecase.monitoring.GetSelfPlansUseCase
import com.example.monitoringapp.usecase.monitoring.dailyreport.GetDateUseCase
import com.example.monitoringapp.usecase.user.GetSelfUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MedicalRecordViewModel @Inject constructor(
    private val getSelfPlansUseCase: GetSelfPlansUseCase,
    private val getDateUseCase: GetDateUseCase,
    private val getSelfUseCase: GetSelfUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetSelfUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewGetSelfStateObservable = _mutableGetSelfUIViewState.asLiveData()

    private val _mutableGetSelfPlansUIViewState =
        MutableLiveData<UIViewState<Plan>>()
    val uiViewGetSelfPlansStateObservable =
        _mutableGetSelfPlansUIViewState.asLiveData()

    private val _mutableGetTemperatureAndSaturationUIViewState =
        MutableLiveData<UIViewState<TemperatureSaturation>>()
    val uiViewGetTemperatureAndSaturationStateObservable =
        _mutableGetTemperatureAndSaturationUIViewState.asLiveData()

    fun getSelf() {
        emitUIGetSelfState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getSelfUseCase()
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetSelfState(UIViewState.Success(data))
                    } else {
                        emitUIGetSelfState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetSelfState(UIViewState.Error(result.exception))
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
                    if (result.data?.data?.size != 0){
                        val data = result.data?.data?.get(result.data.data.size - 1)
                        if (data != null) {
                            emitUIGetSelfPlansState(UIViewState.Success(data))
                        } else {
                            emitUIGetSelfPlansState(UIViewState.Error(Constants.DEFAULT_ERROR))
                        }
                    }else{
                        emitUIGetSelfPlansState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetSelfPlansState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getTemperatureAndSaturation(
        planId: Int,
        dailyReportDateRequest: DailyReportDateRequest
    ) {
        emitUIGetTemperatureAndSaturationState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getDateUseCase(planId, dailyReportDateRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetTemperatureAndSaturationState(UIViewState.Success(data))
                    } else {
                        emitUIGetTemperatureAndSaturationState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetTemperatureAndSaturationState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetSelfState(state: UIViewState<User>) {
        _mutableGetSelfUIViewState.postValue(state)
    }

    private fun emitUIGetSelfPlansState(state: UIViewState<Plan>) {
        _mutableGetSelfPlansUIViewState.postValue(state)
    }

    private fun emitUIGetTemperatureAndSaturationState(state: UIViewState<TemperatureSaturation>) {
        _mutableGetTemperatureAndSaturationUIViewState.postValue(state)
    }

}