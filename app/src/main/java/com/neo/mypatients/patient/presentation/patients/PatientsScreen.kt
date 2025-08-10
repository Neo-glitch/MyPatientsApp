package com.neo.mypatients.patient.presentation.patients

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neo.mypatients.R
import com.neo.mypatients.core.presentation.components.MyPatientsAppScreenScaffold
import com.neo.mypatients.core.presentation.components.appbar.AppBarAction
import com.neo.mypatients.core.presentation.components.appbar.MyPatientsAppTopBar
import com.neo.mypatients.core.presentation.components.divider.MyPatientsAppHorizontalDivider
import com.neo.mypatients.core.presentation.components.input.MyPatientsAppSearchInputField
import com.neo.mypatients.core.presentation.components.loader.MyPatientsAppEmptyScreen
import com.neo.mypatients.core.presentation.components.loader.MyPatientsAppLoadingScreen
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.data.worker.PatientSyncWorker
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.patient.presentation.components.PatientItem
import com.neo.mypatients.patient.utils.PatientSyncWorkManager
import com.neo.mypatients.ui.theme.MyPatientsTheme

@Composable
fun PatientsScreen(
    viewModel: PatientsViewModel = hiltViewModel(),
    onPatientItemClick: (Patient) -> Unit,
    onAddNewPatientItemClick: () -> Unit,
) {
    CheckAndRequestNotificationPermissionRequest()
    val context = LocalContext.current.applicationContext
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    PatientsScreenContent(
        uiState = state.value,
        onPatientItemClick = onPatientItemClick,
        onAddNewPatientItemClick = onAddNewPatientItemClick,
        onSyncPatients = {
            PatientSyncWorkManager(context).enqueOneTimePatientSyncWork()
        },
        onQueryUpdate = {
            viewModel.onQueryUpdate(it)
        }
    )
}

@Composable
private fun PatientsScreenContent(
    uiState: PatientsUiState,
    onPatientItemClick: (Patient) -> Unit,
    onAddNewPatientItemClick: () -> Unit,
    onSyncPatients: () -> Unit,
    onQueryUpdate: (String) -> Unit,
) {
    MyPatientsAppScreenScaffold(
        modifier = Modifier,
        topBar = {
            MyPatientsAppTopBar(
                title = "MyPatientsApp",
                actions = listOf(
                    AppBarAction(
                        icon = R.drawable.ic_cloud_sync,
                        tint = MaterialTheme.colorScheme.secondary,
                        onClick = onSyncPatients,
                    ),
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNewPatientItemClick,
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.secondary,
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add patient"
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                )
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            MyPatientsAppSearchInputField(
                modifier = Modifier.padding(horizontal = 4.dp),
                value = uiState.query,
                onValueChange = onQueryUpdate,
                placeholder = "Search Patients Name"
            )
            Spacer(modifier = Modifier.height(20.dp))

            MyPatientsAppHorizontalDivider(height = 2.dp)
            Spacer(modifier = Modifier.height(20.dp))

            when (uiState.loadState) {
                PatientsLoadState.Empty -> EmptyStateContent(
                    query = uiState.query,
                )

                PatientsLoadState.Loading -> LoadingStateContent()

                PatientsLoadState.Success -> PatientsListContent(
                    patients = uiState.patients,
                    onPatientItemClick = onPatientItemClick,
                )

                is PatientsLoadState.Error -> ErrorStateContent(
                    message = stringResource(uiState.loadState.message),
                )

                else -> Unit
            }
        }
    }
}

@Composable
private fun EmptyStateContent(modifier: Modifier = Modifier, query: String) {

    val emptyPatientsInDbDescription = stringResource(R.string.empty_patients_in_db_desc)
    val emptySearchPatientsDescription = stringResource(R.string.empty_search_patients_desc)

    val description = remember(query) {
        if (query.isBlank()) emptyPatientsInDbDescription else emptySearchPatientsDescription
    }

    MyPatientsAppEmptyScreen(
        modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        title = "No patients found",
        subtitle = description
    )
}

@Composable
private fun LoadingStateContent(modifier: Modifier = Modifier) {
    MyPatientsAppLoadingScreen(
        indicatorSize = 32.dp,
        modifier = modifier.padding(horizontal = 16.dp),
        loadingMessage = "Loading patients...",
    )
}

@Composable
private fun PatientsListContent(
    modifier: Modifier = Modifier,
    patients: List<Patient>,
    onPatientItemClick: (Patient) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            key = { patient -> patient.id },
            items = patients
        ) { patient ->
            PatientItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                patient = patient,
                onItemClick = onPatientItemClick
            )
        }
    }
}

@Composable
private fun ErrorStateContent(modifier: Modifier = Modifier, message: String) {
    MyPatientsAppEmptyScreen(
        modifier = modifier,
        title = "Something went wrong",
        subtitle = message,
        image = R.drawable.ic_error_patients
    )
}

@Composable
fun CheckAndRequestNotificationPermissionRequest() {
    val context = LocalContext.current

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(context, "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun PatientsScreenLightModePreview() {
    MyPatientsTheme {
        Surface {
            PatientsScreenContent(
                onPatientItemClick = {},
                onAddNewPatientItemClick = {},
                onSyncPatients = {},
                onQueryUpdate = {},
                uiState = PatientsUiState(
                    patients = listOf(
                        Patient(
                            name = "John Doe",
                            age = 30,
                            gender = Gender.Male,
                            phoneNumber = "123-456-7890",
                            medicalCondition = "Hypertension",
                            syncStatus = SyncStatus.SYNCED,
                            id = 1,
                        ),
                        Patient(
                            name = "John Doe",
                            age = 30,
                            gender = Gender.Male,
                            phoneNumber = "123-456-7890",
                            medicalCondition = "Hypertension",
                            syncStatus = SyncStatus.SYNCED,
                            id = 2,
                        ),
                    ),
                    loadState = PatientsLoadState.Success
                )
            )
        }
    }
}

@Preview(
    showBackground = true, apiLevel = 34,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun PatientsScreenDarkModePreview() {
    MyPatientsTheme {
        Surface {
            PatientsScreenContent(
                onPatientItemClick = {},
                onAddNewPatientItemClick = {},
                onSyncPatients = {},
                onQueryUpdate = {},
                uiState = PatientsUiState(
                    patients = listOf(
                        Patient(
                            name = "John Doe",
                            age = 30,
                            gender = Gender.Male,
                            phoneNumber = "123-456-7890",
                            medicalCondition = "Hypertension",
                            syncStatus = SyncStatus.SYNCED,
                            id = 1,
                        ),
                        Patient(
                            name = "John Doe",
                            age = 30,
                            gender = Gender.Male,
                            phoneNumber = "123-456-7890",
                            medicalCondition = "Hypertension",
                            syncStatus = SyncStatus.SYNCED,
                            id = 2,
                        ),
                    ),
                    loadState = PatientsLoadState.Success
                )
            )
        }
    }
}
