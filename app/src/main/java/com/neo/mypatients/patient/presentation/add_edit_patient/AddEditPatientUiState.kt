package com.neo.mypatients.patient.presentation.add_edit_patient

import com.neo.mypatients.patient.data.datasources.local.model.Gender

data class AddEditPatientUiState(
    val name: String = "",
    val age: Int = 0,
    val gender: Gender? = null,
    val phoneNumber: String = "",
    val medicalCondition: String = "",
    val loadState: AddEditPatientLoadState = AddEditPatientLoadState.Idle
)

sealed interface AddEditPatientUiEvent {
    data object OnSuccess: AddEditPatientUiEvent
    data class OnError(val errorMsgStringRes: Int): AddEditPatientUiEvent
}

sealed interface AddEditPatientLoadState {
    data object Idle: AddEditPatientLoadState
    data object Loading: AddEditPatientLoadState
    data object Error: AddEditPatientLoadState
}
