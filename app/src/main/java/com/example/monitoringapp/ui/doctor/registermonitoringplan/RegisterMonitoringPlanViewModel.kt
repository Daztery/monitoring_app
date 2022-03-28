package com.example.monitoringapp.ui.doctor.registermonitoringplan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Emergency
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.PlanPrescriptionRequest
import com.example.monitoringapp.data.network.request.PlanRequest
import com.example.monitoringapp.usecase.emergencytype.GetAllEmergencyUseCase
import com.example.monitoringapp.usecase.monitoring.CreatePlanUseCase
import com.example.monitoringapp.usecase.prescription.CreatePlanPrescriptionUseCase
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
    private val getPatientUseCase: GetPatientUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetPatientUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewGetPatientStateObservable = _mutableGetPatientUIViewState.asLiveData()

    private val _mutableGetEmergencyUIViewState = MutableLiveData<UIViewState<List<Emergency>>>()
    val uiViewGetEmergencyStateObservable = _mutableGetEmergencyUIViewState.asLiveData()

    private val _mutableCreatePlanUIViewState = MutableLiveData<UIViewState<Plan>>()
    val uiViewCreatePlanStateObservable = _mutableCreatePlanUIViewState.asLiveData()

    private val _mutableCreatePlanPrescriptionUIViewState =
        MutableLiveData<UIViewState<Prescription>>()
    val uiViewCreatePlanPrescriptionStateObservable =
        _mutableCreatePlanPrescriptionUIViewState.asLiveData()

    fun getPatient(identification: Int) {
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

    fun getEmergency() {
        emitUIGetEmergencyState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getAllEmergencyUseCase()
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetEmergencyState(UIViewState.Success(data))
                    } else {
                        emitUIGetEmergencyState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetEmergencyState(UIViewState.Error(result.exception))
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

    private fun emitUIGetEmergencyState(state: UIViewState<List<Emergency>>) {
        _mutableGetEmergencyUIViewState.postValue(state)
    }

    private fun emitUICreatePlanState(state: UIViewState<Plan>) {
        _mutableCreatePlanUIViewState.postValue(state)
    }

    private fun emitUICreatePlanPrescriptionState(state: UIViewState<Prescription>) {
        _mutableCreatePlanPrescriptionUIViewState.postValue(state)
    }

}