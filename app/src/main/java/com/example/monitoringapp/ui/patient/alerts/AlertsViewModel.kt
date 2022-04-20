package com.example.monitoringapp.ui.patient.alerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Alert
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.usecase.alert.GetSelfAlertsUseCase
import com.example.monitoringapp.usecase.user.GetSelfUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val getSelfUseCase: GetSelfUseCase,
    private val getSelfAlertsUseCase: GetSelfAlertsUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetSelfUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewGetSelfStateObservable = _mutableGetSelfUIViewState.asLiveData()

    private val _mutableGetSelfAlertUIViewState = MutableLiveData<UIViewState<List<Alert>>>()
    val uiViewGetSelfAlertStateObservable = _mutableGetSelfAlertUIViewState.asLiveData()

    fun getSelf() {
        viewModelScope.launch {
            emitUIGetSelfState(UIViewState.Loading)
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

    fun getSelfAlerts() {
        viewModelScope.launch {
            emitUIGetSelfAlertState(UIViewState.Loading)
            val result = withContext(dispatchers.io) {
                getSelfAlertsUseCase()
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetSelfAlertState(UIViewState.Success(data))
                    } else {
                        emitUIGetSelfAlertState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetSelfAlertState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetSelfState(state: UIViewState<User>) {
        _mutableGetSelfUIViewState.postValue(state)
    }

    private fun emitUIGetSelfAlertState(state: UIViewState<List<Alert>>) {
        _mutableGetSelfAlertUIViewState.postValue(state)
    }

}