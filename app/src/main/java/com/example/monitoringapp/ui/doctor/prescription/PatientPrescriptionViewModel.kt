package com.example.monitoringapp.ui.doctor.prescription

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.usecase.monitoring.GetSelfPlansUseCase
import com.example.monitoringapp.usecase.prescription.GetSelfPrescriptionUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PatientPrescriptionViewModel @Inject constructor(
    private val getSelfPrescriptionUseCase: GetSelfPrescriptionUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetSelfPrescriptionUIViewState =
        MutableLiveData<UIViewState<List<Prescription>>>()
    val uiViewGetSelfPrescriptionStateObservable =
        _mutableGetSelfPrescriptionUIViewState.asLiveData()

    fun getSelfPrescriptions(
        from: String,
        to: String
    ) {
        emitUIGetSelfPrescriptionState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getSelfPrescriptionUseCase(true, from, to)
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