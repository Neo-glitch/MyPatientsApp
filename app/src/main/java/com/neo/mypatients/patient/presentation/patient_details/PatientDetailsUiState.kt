package com.neo.mypatients.patient.presentation.patient_details

import com.neo.mypatients.patient.domain.model.Patient

data class PatientDetailsUiState (
    val patient: Patient? = null,
    val loadState: PatientDetailsLoadState = PatientDetailsLoadState.Idle
)

sealed interface PatientDetailsUiEvent {
    data object OnSuccess: PatientDetailsUiEvent
    data object OnError: PatientDetailsUiEvent
}

sealed interface PatientDetailsLoadState {
    data object Idle: PatientDetailsLoadState
    data object Loading: PatientDetailsLoadState
    data object Success: PatientDetailsLoadState
}