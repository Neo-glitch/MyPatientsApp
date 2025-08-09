package com.neo.mypatients.patient.presentation.add_edit_patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.core.utils.toUiText
import com.neo.mypatients.patient.domain.usecases.AddEditPatientUseCase
import com.neo.mypatients.patient.domain.usecases.GetPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditPatientViewModel @Inject constructor(
    private val addEditPatientUseCase: AddEditPatientUseCase,
    private val getPatientUseCase: GetPatientUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(AddEditPatientUiState())
    val uiState: StateFlow<AddEditPatientUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AddEditPatientUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun loadPatient(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = AddEditPatientLoadState.Loading) }

            val patientResponse = getPatientUseCase(id)
            when (patientResponse) {
                is Resource.Error -> {
                    _uiState.update { it.copy(loadState = AddEditPatientLoadState.Error) }
                    _uiEvent.emit(AddEditPatientUiEvent.OnError(
                        errorMsgStringRes = patientResponse.error.toUiText()
                    ))
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            loadState = AddEditPatientLoadState.Idle,
                            name = patientResponse.data.name,
                            age = patientResponse.data.age,
                            gender = patientResponse.data.gender,
                            phoneNumber = patientResponse.data.phoneNumber,
                            medicalCondition = patientResponse.data.medicalCondition
                        )
                    }
                }
            }
        }
    }

    fun savePatientDetails() {

    }
}