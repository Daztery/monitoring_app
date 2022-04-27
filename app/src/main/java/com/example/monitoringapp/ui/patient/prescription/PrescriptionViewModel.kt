package com.example.monitoringapp.ui.patient.prescription

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.model.Status
import com.example.monitoringapp.usecase.monitoring.GetSelfPlansUseCase
import com.example.monitoringapp.usecase.prescription.GetPlanPrescriptionUseCase
import com.example.monitoringapp.usecase.prescription.GetSelfPrescriptionUseCase
import com.example.monitoringapp.usecase.report.GetPatientStatusUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PrescriptionViewModel @Inject constructor(
    private val getSelfPrescriptionUseCase: GetSelfPrescriptionUseCase,
    private val getPlanPrescriptionUseCase: GetPlanPrescriptionUseCase,
    private val getPatientStatusUseCase: GetPatientStatusUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetSelfPrescriptionUIViewState =
        MutableLiveData<UIViewState<List<Prescription>>>()
    val uiViewGetSelfPrescriptionStateObservable =
        _mutableGetSelfPrescriptionUIViewState.asLiveData()

    private val _mutableGetPatientPrescriptionUIViewState =
        MutableLiveData<UIViewState<Prescription>>()
    val uiViewGetPatientPrescriptionStateObservable =
        _mutableGetPatientPrescriptionUIViewState.asLiveData()

    private val _mutableGetPatientStatusUIViewState =
        MutableLiveData<UIViewState<List<Status>>>()
    val uiViewGetPatientStatusStateObservable =
        _mutableGetPatientStatusUIViewState.asLiveData()

    fun getSelfPrescriptions(
        from: String,
        to: String
    ) {
        emitUIGetSelfPrescriptionState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getSelfPrescriptionUseCase(from, to)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetSelfPrescriptionState(UIViewState.Success(data))
                    } else {
                        emitUIGetSelfPrescriptionState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetSelfPrescriptionState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getPlanPrescription(
        planId: Int
    ) {
        emitUIGetPlanPrescriptionState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPlanPrescriptionUseCase(planId)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetPlanPrescriptionState(UIViewState.Success(data))
                    } else {
                        emitUIGetPlanPrescriptionState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetPlanPrescriptionState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    fun getPatientStatus(
        from: String,
        to: String
    ) {
        emitUIGetPatientStatusState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getPatientStatusUseCase(false, from, to)
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetPatientStatusState(UIViewState.Success(data))
                    } else {
                        emitUIGetPatientStatusState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetPatientStatusState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetPlanPrescriptionState(state: UIViewState<Prescription>) {
        _mutableGetPatientPrescriptionUIViewState.postValue(state)
    }

    private fun emitUIGetSelfPrescriptionState(state: UIViewState<List<Prescription>>) {
        _mutableGetSelfPrescriptionUIViewState.postValue(state)
    }

    private fun emitUIGetPatientStatusState(state: UIViewState<List<Status>>) {
        _mutableGetPatientStatusUIViewState.postValue(state)
    }

}