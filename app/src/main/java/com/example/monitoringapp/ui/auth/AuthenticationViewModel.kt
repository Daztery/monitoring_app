package com.example.monitoringapp.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.MedicalCenter
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.RecoverPasswordRequest
import com.example.monitoringapp.data.network.request.SignInRequest
import com.example.monitoringapp.data.network.request.UpdatePasswordRequest
import com.example.monitoringapp.usecase.auth.LoginDoctorUseCase
import com.example.monitoringapp.usecase.auth.LoginPatientUseCase
import com.example.monitoringapp.usecase.auth.RecoverPasswordUseCase
import com.example.monitoringapp.usecase.auth.UpdatePasswordUseCase
import com.example.monitoringapp.usecase.medicalcenter.GetMedicalCenterUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginDoctorUseCase: LoginDoctorUseCase,
    private val loginPatientUseCase: LoginPatientUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val recoverPasswordUseCase: RecoverPasswordUseCase,
    private val getMedicalCenterUseCase: GetMedicalCenterUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableLoginDoctorUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewLoginDoctorStateObservable = _mutableLoginDoctorUIViewState.asLiveData()

    private val _mutableLoginPatientUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewLoginPatientStateObservable = _mutableLoginPatientUIViewState.asLiveData()

    private val _mutableRecoverPasswordUIViewState = MutableLiveData<UIViewState<String>>()
    val uiViewRecoverPasswordStateObservable = _mutableRecoverPasswordUIViewState.asLiveData()

    private val _mutableUpdatePasswordUIViewState = MutableLiveData<UIViewState<String>>()
    val uiViewUpdatePasswordStateObservable = _mutableUpdatePasswordUIViewState.asLiveData()

    private val _mutableGetMedicalCenterUIViewState = MutableLiveData<UIViewState<MedicalCenter>>()
    val uiViewGetMedicalCenterStateObservable = _mutableGetMedicalCenterUIViewState.asLiveData()

    fun loginDoctor(user: SignInRequest) {
        emitUILoginDoctorState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                loginDoctorUseCase(user)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUILoginDoctorState(UIViewState.Success(data))
                    } else {
                        emitUILoginDoctorState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUILoginDoctorState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun loginPatient(user: SignInRequest) {
        emitUILoginPatientState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                loginPatientUseCase(user)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUILoginPatientState(UIViewState.Success(data))
                    } else {
                        emitUILoginPatientState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUILoginPatientState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun recoverPassword(recoverPasswordRequest: RecoverPasswordRequest) {
        emitUIRecoverPasswordState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                recoverPasswordUseCase(recoverPasswordRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIRecoverPasswordState(UIViewState.Success(data))
                    } else {
                        emitUIRecoverPasswordState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIRecoverPasswordState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun updatePassword(updatePasswordRequest: UpdatePasswordRequest) {
        emitUIUpdatePasswordState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                updatePasswordUseCase(updatePasswordRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIUpdatePasswordState(UIViewState.Success(data))
                    } else {
                        emitUIUpdatePasswordState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIUpdatePasswordState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getMedicalCenter(id: Int) {
        emitUIGetMedicalCenterState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getMedicalCenterUseCase(id)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetMedicalCenterState(UIViewState.Success(data))
                    } else {
                        emitUIGetMedicalCenterState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetMedicalCenterState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUILoginDoctorState(state: UIViewState<User>) {
        _mutableLoginDoctorUIViewState.postValue(state)
    }

    private fun emitUILoginPatientState(state: UIViewState<User>) {
        _mutableLoginPatientUIViewState.postValue(state)
    }

    private fun emitUIRecoverPasswordState(state: UIViewState<String>) {
        _mutableRecoverPasswordUIViewState.postValue(state)
    }

    private fun emitUIUpdatePasswordState(state: UIViewState<String>) {
        _mutableUpdatePasswordUIViewState.postValue(state)
    }

    private fun emitUIGetMedicalCenterState(state: UIViewState<MedicalCenter>) {
        _mutableGetMedicalCenterUIViewState.postValue(state)
    }

}