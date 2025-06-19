package com.example.monitoringapp.ui.doctor.searchpatient

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.UpdatePatientRequest
import com.example.monitoringapp.usecase.user.GetPatientUseCase
import com.example.monitoringapp.usecase.user.UpdatePatientUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditPatientViewModel @Inject constructor(
    private val getPatientUseCase: GetPatientUseCase,
    private val updatePatientUseCase: UpdatePatientUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableUpdatePatientUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewUpdatePatientStateObservable = _mutableUpdatePatientUIViewState.asLiveData()

    private val _mutableGetPatientUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewGetPatientStateObservable = _mutableGetPatientUIViewState.asLiveData()

    fun getPatient(identification: String) {
        emitUIGetPatientState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPatientUseCase(identification)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetPatientState(UIViewState.Success(data))
                    } else {
                        emitUIGetPatientState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetPatientState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun updatePatient(
        id: Int,
        updatePatientRequest: UpdatePatientRequest
    ) {
        emitUIUpdatePatientState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                updatePatientUseCase(id, updatePatientRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.clearRes?.data
                    if (data != null) {
                        emitUIUpdatePatientState(UIViewState.Success(data))
                    } else {
                        emitUIUpdatePatientState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIUpdatePatientState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIUpdatePatientState(state: UIViewState<User>) {
        _mutableUpdatePatientUIViewState.postValue(state)
    }

    private fun emitUIGetPatientState(state: UIViewState<User>) {
        _mutableGetPatientUIViewState.postValue(state)
    }

}