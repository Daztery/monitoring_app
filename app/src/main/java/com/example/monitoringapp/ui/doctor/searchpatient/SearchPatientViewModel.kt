package com.example.monitoringapp.ui.doctor.searchpatient

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.usecase.user.GetPatientUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchPatientViewModel @Inject constructor(
    private val getPatientUseCase: GetPatientUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

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

    private fun emitUIGetPatientState(state: UIViewState<User>) {
        _mutableGetPatientUIViewState.postValue(state)
    }

}