package com.example.monitoringapp.ui.patient.medicalrecord

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.usecase.user.GetSelfUseCase
import com.example.monitoringapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MedicalRecordViewModel @Inject constructor(
    private val getSelfUseCase: GetSelfUseCase,
    private val dispatchers: DispatchersUtil,
) : ViewModel() {

    private val _mutableGetSelfUIViewState = MutableLiveData<UIViewState<User>>()
    val uiViewGetSelfStateObservable = _mutableGetSelfUIViewState.asLiveData()

    fun getSelf() {
        emitUIGetSelfState(UIViewState.Loading)
        viewModelScope.launch {
            val result = withContext(dispatchers.io) {
                getSelfUseCase()
            }
            when (result) {
                is OperationResult.Success -> {
                    val data = result.data?.data
                    if (data != null) {
                        emitUIGetSelfState(UIViewState.Success(data))
                    } else {
                        emitUIGetSelfState(UIViewState.Error(Constants.DEFAULT_ERROR))
                    }
                }
                is OperationResult.Error -> {
                    emitUIGetSelfState(UIViewState.Error(result.exception))
                }
            }
        }
    }

    private fun emitUIGetSelfState(state: UIViewState<User>) {
        _mutableGetSelfUIViewState.postValue(state)
    }

}