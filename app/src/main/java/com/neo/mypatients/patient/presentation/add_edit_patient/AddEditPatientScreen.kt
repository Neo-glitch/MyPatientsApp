package com.neo.mypatients.patient.presentation.add_edit_patient

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddEditPatientScreen(
    viewModel: AddEditPatientViewModel = hiltViewModel(),
    patientId: Long?,
    onEditCompleted: () -> Unit,
    onBackPress: () -> Unit,
) {

}