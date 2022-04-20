package com.example.monitoringapp.ui.doctor.registermonitoringplan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.*
import com.example.monitoringapp.data.network.request.PlanPrescriptionRequest
import com.example.monitoringapp.data.network.request.PlanRequest
import com.example.monitoringapp.usecase.emergencytype.GetAllEmergencyUseCase
import com.example.monitoringapp.usecase.monitoring.CreatePlanUseCase
import com.example.monitoringapp.usecase.prescription.CreatePlanPrescriptionUseCase
import com.example.monitoringapp.usecase.prioritytype.GetAllPriorityUseCase
import com.example.monitoringapp.usecase.user.GetPatientUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterMonitoringPlanViewModel @Inject constructor(
    private val createPlanPrescriptionUseCase: CreatePlanPrescriptionUseCase,
    private val createPlanUseCase: CreatePlanUseCase,
    private val getAllEmergencyUseCase: GetAllEmergencyUseCase,
    private val getAllPriorityUseCase: GetAllPriorityUseCase,
    private val getPatientUseCase: GetPatientUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetPatientUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewGetPatientStateObservable = _mutableGetPatientUIViewState.asLiveData()

    private val _mutableGetAllEmergenciesUIViewState = MutableLiveData<UIViewState<List<Emergency>>>()
    val uiViewGetAllEmergenciesStateObservable = _mutableGetAllEmergenciesUIViewState.asLiveData()

    private val _mutableGetAllPrioritiesUIViewState = MutableLiveData<UIViewState<List<Priority>>>()
    val uiViewGetAllPrioritiesStateObservable = _mutableGetAllPrioritiesUIViewState.asLiveData()

    private val _mutableCreatePlanUIViewState = MutableLiveData<UIViewState<Plan>>()
    val uiViewCreatePlanStateObservable = _mutableCreatePlanUIViewState.asLiveData()

    private val _mutableCreatePlanPrescriptionUIViewState =
        MutableLiveData<UIViewState<Prescription>>()
    val uiViewCreatePlanPrescriptionStateObservable =
        _mutableCreatePlanPrescriptionUIViewState.asLiveData()

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

    fun getAllEmergencies() {
        emitUIGetAllEmergenciesState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getAllEmergencyUseCase()
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetAllEmergenciesState(UIViewState.Success(data))
                    } else {
                        emitUIGetAllEmergenciesState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetAllEmergenciesState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getAllPriorities() {
        emitUIGetAllPrioritiesState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getAllPriorityUseCase()
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetAllPrioritiesState(UIViewState.Success(data))
                    } else {
                        emitUIGetAllPrioritiesState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetAllPrioritiesState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun createPlan(planRequest: PlanRequest) {
        emitUICreatePlanState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                createPlanUseCase(planRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUICreatePlanState(UIViewState.Success(data))
                    } else {
                        emitUICreatePlanState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUICreatePlanState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun createPlanPrescription(
        planId: Int,
        planPrescriptionRequest: PlanPrescriptionRequest
    ) {
        emitUICreatePlanPrescriptionState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                createPlanPrescriptionUseCase(planId, planPrescriptionRequest)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUICreatePlanPrescriptionState(UIViewState.Success(data))
                    } else {
                        emitUICreatePlanPrescriptionState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUICreatePlanPrescriptionState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetPatientState(state: UIViewState<User>) {
        _mutableGetPatientUIViewState.postValue(state)
    }

    private fun emitUIGetAllEmergenciesState(state: UIViewState<List<Emergency>>) {
        _mutableGetAllEmergenciesUIViewState.postValue(state)
    }

    private fun emitUIGetAllPrioritiesState(state: UIViewState<List<Priority>>) {
        _mutableGetAllPrioritiesUIViewState.postValue(state)
    }

    private fun emitUICreatePlanState(state: UIViewState<Plan>) {
        _mutableCreatePlanUIViewState.postValue(state)
    }

    private fun emitUICreatePlanPrescriptionState(state: UIViewState<Prescription>) {
        _mutableCreatePlanPrescriptionUIViewState.postValue(state)
    }

}