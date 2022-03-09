package com.example.monitoringapp.ui.patient.medicalhistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MedicalHistoryViewModel @Inject constructor(
    private val getSelfPrescriptionUseCase: GetSelfPrescriptionUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetSelfPrescriptionUIViewState =
        MutableLiveData<UIViewState<List<Prescription>>>()
    val uiViewGetSelfPrescriptionStateObservable =
        _mutableGetSelfPrescriptionUIViewState.asLiveData()

    fun getSelfPrescription(active: Boolean, from: String, to: String) {
        emitUIGetSelfPrescriptionState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getSelfPrescriptionUseCase(active, from, to)
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

    private fun emitUIGetSelfPrescriptionState(state: UIViewState<List<Prescription>>) {
        _mutableGetSelfPrescriptionUIViewState.postValue(state)
    }

}