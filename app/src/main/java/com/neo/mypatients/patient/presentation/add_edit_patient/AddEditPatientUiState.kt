package com.neo.mypatients.patient.presentation.add_edit_patient

import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.domain.model.Patient

data class AddEditPatientUiState(
    val patient: Patient? = null,
    val name: String = "",
    val age: String = "",
    val gender: Gender? = null,
    val phoneNumber: String = "",
    val medicalCondition: String = "",
    val loadState: AddEditPatientLoadState = AddEditPatientLoadState.Idle,
    val syncStatus: SyncStatus = SyncStatus.SYNCED
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
