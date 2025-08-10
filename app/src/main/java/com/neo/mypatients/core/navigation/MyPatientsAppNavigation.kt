package com.neo.mypatients.core.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.neo.mypatients.patient.presentation.add_edit_patient.AddEditPatientScreen
import com.neo.mypatients.patient.presentation.patient_details.PatientDetailsScreen
import com.neo.mypatients.patient.presentation.patients.PatientsScreen

@Composable
fun MyPatientsAppNavigation(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = PatientsScreen,
    ) {
        composable<PatientsScreen> {
            PatientsScreen(
                onPatientItemClick = { patient ->
                    navController.navigate(
                        PatientDetailsScreen(patient.id)
                    )
                },
                onAddNewPatientItemClick = {
                    navController.navigate(
                        AddEditPatientDetailsScreen()
                    )
                }
            )
        }

        composable<PatientDetailsScreen> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<PatientDetailsScreen>()
            PatientDetailsScreen(
                patientId = args.patientId,
                onEditPatientClick = { patientId ->
                    navController.navigate(
                        AddEditPatientDetailsScreen(patientId)
                    )
                },
                onDeletePatientSuccess = {
                    Toast.makeText(context, "Patient Info deleted successfully", Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                },
                onBackPress = {
                    navController.navigateUp()
                }
            )
        }

        composable<AddEditPatientDetailsScreen> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<AddEditPatientDetailsScreen>()
            AddEditPatientScreen(
                patientId = args.patientId,
                onEditCompleted = {
                    Toast.makeText(context, "Patient Info updated successfully", Toast.LENGTH_SHORT).show()
                    navController.popBackStack(route = PatientsScreen, inclusive = false)
                },
                onBackPress = {
                    navController.navigateUp()
                }
            )
        }
    }
}