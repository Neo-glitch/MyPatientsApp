package com.neo.mypatients.patient.presentation.patients

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.neo.mypatients.patient.domain.model.Patient

@Composable
fun PatientsScreen(
    viewModel: PatientsViewModel = hiltViewModel(),
    onPatientItemClick: (Patient) -> Unit,
    onAddNewPatientItemClick: () -> Unit,
    onDeletePatientItemClick: (Patient) -> Unit,
) {


}

@Composable
private fun PatientsScreenContent(modifier: Modifier = Modifier) {

}