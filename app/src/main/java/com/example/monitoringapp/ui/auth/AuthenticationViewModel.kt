package com.example.monitoringapp.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.SignInRequest
import com.example.monitoringapp.usecase.auth.LoginDoctorUseCase
import com.example.monitoringapp.usecase.auth.LoginPatientUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginDoctorUseCase: LoginDoctorUseCase,
    private val loginPatientUseCase: LoginPatientUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {


    private val _mutableLoginDoctorUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewLoginDoctorStateObservable = _mutableLoginDoctorUIViewState.asLiveData()

    private val _mutableLoginPatientUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewLoginPatientStateObservable = _mutableLoginPatientUIViewState.asLiveData()

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

    /*fun signup(user: SignUpRequest) {
        emitUISignUpState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                signUpUseCase(user)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.userData
                    if (data != null) {
                        emitUISignUpState(UIViewState.Success(data))
                    } else {
                        emitUISignUpState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUISignUpState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun logout(deviceUUID: String) {
        viewModelScope.launch(dispatchers.io) {
            val result = logoutUseCase(deviceUUID)
            withContext(dispatchers.main) {
                when (result) {
                    is OperationResult.Success -> {
                        val response = result.data ?: false
                        if (response) {
                            emitUILogoutState(UIViewState.Success(response))
                        } else {
                            emitUILogoutState(UIViewState.Error(Constants.DEFAULT_ERROR))
                        }
                    }
                    is OperationResult.Error -> {
                        emitUILogoutState(UIViewState.Error(result.exception))
                    }
                }
            }
        }
    }*/

    private fun emitUILoginDoctorState(state: UIViewState<User>) {
        _mutableLoginDoctorUIViewState.postValue(state)
    }

    private fun emitUILoginPatientState(state: UIViewState<User>) {
        _mutableLoginPatientUIViewState.postValue(state)
    }
}