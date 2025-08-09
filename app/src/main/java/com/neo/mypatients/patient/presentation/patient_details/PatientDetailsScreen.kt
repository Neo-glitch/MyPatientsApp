package com.neo.mypatients.patient.presentation.patient_details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PatientDetailsScreen(
    viewModel: PatientDetailsViewModel = hiltViewModel(),
    patientId: Long?,
    onEditPatientClick: (id: Long) -> Unit,
    onDeletePatientSuccess: () -> Unit,
    onBackPress: () -> Unit,
) {

}