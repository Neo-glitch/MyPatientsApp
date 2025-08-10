package com.neo.mypatients.patient.presentation.patient_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.core.utils.orZero
import com.neo.mypatients.core.utils.toUiText
import com.neo.mypatients.patient.domain.usecases.DeletePatientUseCase
import com.neo.mypatients.patient.domain.usecases.GetPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientDetailsViewModel @Inject constructor(
    private val getPatientUseCase: GetPatientUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(PatientDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PatientDetailsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun loadPatient(patientId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loadState = PatientDetailsLoadState.PatientGetLoading)

            val patientResponse = getPatientUseCase(patientId)
            when (patientResponse) {
                is Resource.Error -> {
                    _uiState.update { it.copy(loadState = PatientDetailsLoadState.Idle) }
                    _uiEvent.emit(PatientDetailsUiEvent.OnError(
                        errorMsgStringRes = patientResponse.error.toUiText()
                    ))
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(patient = patientResponse.data, loadState = PatientDetailsLoadState.Success) }
                }
            }
        }
    }

    fun deletePatient() {
        viewModelScope.launch {
            if (_uiState.value.patient == null) {
                _uiEvent.emit(PatientDetailsUiEvent.OnError(
                    errorMsgStringRes = DataError.Local.DATABASE_ERROR.toUiText()
                ))
                return@launch
            }

            _uiState.update { it.copy(loadState = PatientDetailsLoadState.PatientDeleteLoading) }
            val deleteResponse = deletePatientUseCase(_uiState.value.patient!!)
            when (deleteResponse) {
                is Resource.Error -> {
                    _uiState.update { it.copy(loadState = PatientDetailsLoadState.Idle) }
                    _uiEvent.emit(PatientDetailsUiEvent.OnError(
                        errorMsgStringRes = deleteResponse.error.toUiText()
                    ))
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(loadState = PatientDetailsLoadState.Idle) }
                    _uiEvent.emit(PatientDetailsUiEvent.OnDeleteSuccess)
                }
            }

        }
    }
}