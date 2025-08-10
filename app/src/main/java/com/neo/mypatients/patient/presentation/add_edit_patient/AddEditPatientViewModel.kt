package com.neo.mypatients.patient.presentation.add_edit_patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.core.utils.toUiText
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.domain.model.Patient
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
                            patient = patientResponse.data,
                            name = patientResponse.data.name,
                            age = patientResponse.data.age.toString(),
                            gender = patientResponse.data.gender,
                            phoneNumber = patientResponse.data.phoneNumber,
                            medicalCondition = patientResponse.data.medicalCondition,
                            syncStatus = patientResponse.data.syncStatus
                        )
                    }
                }
            }
        }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateAge(age: String) {
        _uiState.update { it.copy(age = age) }
    }

    fun updateGender(gender: Gender) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updateMedicalCondition(medicalCondition: String) {
        _uiState.update { it.copy(medicalCondition = medicalCondition) }
    }


    fun savePatientDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = AddEditPatientLoadState.Loading) }

            val patientResponse = addEditPatientUseCase(
                id = uiState.value.patient?.id,
                name = uiState.value.name,
                age = uiState.value.age.toIntOrNull(),
                gender = uiState.value.gender ?: Gender.Male,
                phoneNumber = uiState.value.phoneNumber,
                medicalCondition = uiState.value.medicalCondition,
                syncStatus = uiState.value.patient?.syncStatus,
            )


            when (patientResponse) {
                is Resource.Error -> {
                    _uiState.update { it.copy(loadState = AddEditPatientLoadState.Error) }
                    _uiEvent.emit(AddEditPatientUiEvent.OnError(
                        errorMsgStringRes = patientResponse.error.toUiText()
                    ))
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(loadState = AddEditPatientLoadState.Idle) }
                    _uiEvent.emit(AddEditPatientUiEvent.OnSuccess)
                }
            }
        }
    }
}