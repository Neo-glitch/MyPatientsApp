package com.neo.mypatients.patient.presentation.patient_details

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neo.mypatients.R
import com.neo.mypatients.core.presentation.components.MyPatientsAppScreenScaffold
import com.neo.mypatients.core.presentation.components.appbar.AppBarAction
import com.neo.mypatients.core.presentation.components.appbar.MyPatientsAppTopBar
import com.neo.mypatients.core.presentation.components.buttons.MyPatientsAppPrimaryButton
import com.neo.mypatients.core.presentation.components.input.MyPatientsAppTextInputField
import com.neo.mypatients.core.presentation.components.loader.MyPatientsAppLoadingScreen
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.patient.presentation.components.AddEditPatientForm
import com.neo.mypatients.patient.presentation.components.PatientProfileHeader
import com.neo.mypatients.ui.theme.MyPatientsTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PatientDetailsScreen(
    viewModel: PatientDetailsViewModel = hiltViewModel(),
    patientId: Long?,
    onEditPatientClick: (id: Long) -> Unit,
    onDeletePatientSuccess: () -> Unit,
    onBackPress: () -> Unit,
) {

    var showDeleteAlertDialog by remember { mutableStateOf(false) }
    var showErrorAlertDialog by remember { mutableStateOf(false) }

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        patientId?.let { viewModel.loadPatient(it)  }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is PatientDetailsUiEvent.OnError -> {
                    showErrorAlertDialog = true
                }
                PatientDetailsUiEvent.OnDeleteSuccess -> {
                    onDeletePatientSuccess()
                }
            }
        }
    }

    PatientDetailsScreenContent(
        uiState = state.value,
        onEditPatientClick = onEditPatientClick,
        onDeletePatientClick = {
            showDeleteAlertDialog = true
        },
        onBackPress = onBackPress,
    )
}

@Composable
private fun PatientDetailsScreenContent(
    uiState: PatientDetailsUiState,
    onEditPatientClick: (id: Long) -> Unit,
    onDeletePatientClick: () -> Unit,
    onBackPress: () -> Unit,
) {
    MyPatientsAppScreenScaffold(
        modifier = Modifier,
        topBar = {
            MyPatientsAppTopBar(
                title = "Patient Details",
                navigationAction = AppBarAction(
                    icon = R.drawable.ic_back,
                    onClick = onBackPress
                ),
                actions = listOf(
                    AppBarAction(
                        icon = R.drawable.ic_delete,
                        tint = MaterialTheme.colorScheme.error,
                        onClick = onDeletePatientClick
                    ),
                )
            )
        },
    ) { innerPadding ->
        when (uiState.loadState) {
            PatientDetailsLoadState.PatientGetLoading -> {
                LoadingStateContent(
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                    ),
                    loadingMessage = "Loading patient details..."

                )
            }
            PatientDetailsLoadState.PatientDeleteLoading-> {
                LoadingStateContent(
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                    ),
                    loadingMessage = "Deleting patient..."
                )
            }
            PatientDetailsLoadState.Success -> {
                LoadedStateContent(
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                    ),
                    onEditPatientClick = onEditPatientClick,
                    patient = uiState.patient!!
                )
            }
            else -> Unit
        }
    }
}

@Composable
private fun LoadingStateContent(modifier: Modifier = Modifier, loadingMessage: String) {
    MyPatientsAppLoadingScreen(
        modifier = modifier.padding(horizontal = 16.dp),
        loadingMessage = loadingMessage,
    )
}

@Composable
private fun LoadedStateContent(modifier : Modifier = Modifier, patient: Patient, onEditPatientClick: (id: Long) -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        PatientProfileHeader()
        Spacer(modifier = Modifier.height(40.dp))

        PatientDetailsSection(patient = patient, modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(32.dp))
        MyPatientsAppPrimaryButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Edit Patient Details",
            onClick = {
                onEditPatientClick(patient.id)
            }
        )
    }
}

@Composable
private fun PatientDetailsSection(
    patient: Patient,
    modifier: Modifier = Modifier,
) {
    AddEditPatientForm(
        modifier = modifier.verticalScroll(rememberScrollState()),
        fullName = patient.name,
        age = patient.age.toString(),
        gender = patient.gender,
        phoneNumber = patient.phoneNumber,
        medicalCondition = patient.medicalCondition,
        isReadOnly = true
    )
}

@Preview(showBackground = true, apiLevel = 34,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun PatientDetailsScreenLightModePreview() {
    MyPatientsTheme {
        Surface {
            PatientDetailsScreenContent(
                uiState = PatientDetailsUiState(
                    patient = Patient(
                        name = "John Doe",
                        age = 30,
                        gender = Gender.Male,
                        phoneNumber = "1234567890",
                        medicalCondition = "Hypertension",
                        syncStatus = SyncStatus.SYNCED,
                        id = 1,
                    ),
                    loadState = PatientDetailsLoadState.Success
                ),
                onEditPatientClick = {},
                onDeletePatientClick = {},
                onBackPress = {}
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 34,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun PatientDetailsScreenDarkModePreview() {
    MyPatientsTheme {
        Surface {
            PatientDetailsScreenContent(
                uiState = PatientDetailsUiState(
                    patient = Patient(
                        name = "John Doe",
                        age = 30,
                        gender = Gender.Male,
                        phoneNumber = "1234567890",
                        medicalCondition = "Hypertension",
                        syncStatus = SyncStatus.SYNCED,
                        id = 1,
                    ),
                    loadState = PatientDetailsLoadState.Success
                ),
                onEditPatientClick = {},
                onDeletePatientClick = {},
                onBackPress = {}
            )
        }
    }
}

