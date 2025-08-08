package com.neo.mypatients.patient.presentation.patients

import com.neo.mypatients.patient.domain.model.Patient

data class PatientsUiState(
    val patients: List<Patient> = emptyList(),
    val loadState: PatientsLoadState = PatientsLoadState.Idle

)

sealed interface PatientsLoadState {
    data object Idle: PatientsLoadState
    data object Loading: PatientsLoadState
    data object Success: PatientsLoadState
    data object Empty: PatientsLoadState
}