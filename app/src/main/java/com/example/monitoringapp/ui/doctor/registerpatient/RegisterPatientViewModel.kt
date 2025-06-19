package com.example.monitoringapp.ui.doctor.registerpatient

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.network.request.RegisterPatientRequest
import com.example.monitoringapp.data.network.response.RegisterPatientResponse
import com.example.monitoringapp.usecase.auth.RegisterPatientUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterPatientViewModel @Inject constructor(
    private val registerPatientUseCase: RegisterPatientUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableRegisterPatientUIViewState =
        MutableLiveData<UIViewState<RegisterPatientResponse>>()
    val uiViewRegisterPatientStateObservable =
        _mutableRegisterPatientUIViewState.asLiveData()

    fun registerPatient(registerPatientRequest: RegisterPatientRequest) {
        emitUIRegisterPatientState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                registerPatientUseCase(registerPatientRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIRegisterPatientState(UIViewState.Success(data))
                    } else {
                        emitUIRegisterPatientState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIRegisterPatientState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIRegisterPatientState(state: UIViewState<RegisterPatientResponse>) {
        _mutableRegisterPatientUIViewState.postValue(state)
    }

}