package com.neo.mypatients.patient.presentation.add_edit_patient

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neo.mypatients.R
import com.neo.mypatients.core.presentation.components.MyPatientsAppScreenScaffold
import com.neo.mypatients.core.presentation.components.appbar.AppBarAction
import com.neo.mypatients.core.presentation.components.appbar.MyPatientsAppTopBar
import com.neo.mypatients.core.presentation.components.buttons.MyPatientsAppPrimaryButton
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.presentation.components.AddEditPatientForm
import com.neo.mypatients.patient.presentation.components.PatientProfileHeader
import com.neo.mypatients.ui.theme.MyPatientsTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditPatientScreen(
    viewModel: AddEditPatientViewModel = hiltViewModel(),
    patientId: Long?,
    onEditCompleted: () -> Unit,
    onBackPress: () -> Unit,
) {

    var showErrorAlertDialog by remember { mutableStateOf(false) }
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        patientId?.let { viewModel.loadPatient(it) }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is AddEditPatientUiEvent.OnError -> {

                }
                AddEditPatientUiEvent.OnSuccess -> {
                    onEditCompleted()
                }
            }
        }
    }

    AddEditPatientScreenContent(
        uiState = state.value,
        isAddMode = patientId == null,
        onBackPress = onBackPress,
        onSaveClick = {
//            viewModel.sa
        },
        onNameChane = {},
        onAgeChange = {},
        onGenderChange = {},
        onPhoneNumberChange = {},
        onMedicalConditionChange = {}
    )
}

@Composable
private fun AddEditPatientScreenContent(
    uiState: AddEditPatientUiState,
    isAddMode: Boolean,
    onBackPress: () -> Unit,
    onSaveClick: () -> Unit,
    onNameChane: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onMedicalConditionChange: (String) -> Unit,
) {
    val title = remember {
        if (isAddMode) "Add Patient Info" else "Edit Patient Info"
    }
    MyPatientsAppScreenScaffold(
        modifier = Modifier,
        topBar = {
            MyPatientsAppTopBar(
                title = title,
                navigationAction = AppBarAction(
                    icon = R.drawable.ic_back,
                    onClick = onBackPress
                ),
            )
        },
    ) { innerPadding ->
        val isButtonEnabled by remember {
            derivedStateOf {
                uiState.name.isNotBlank() && uiState.age > 0 && uiState.gender != null && uiState.phoneNumber.isNotBlank() && uiState.medicalCondition.isNotBlank()
            }
        }
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                )
                .fillMaxSize()
        ) {
            AddEditMainContent(
                modifier = Modifier.weight(1f),
                name = uiState.name,
                age = uiState.age,
                gender = uiState.gender,
                phoneNumber = uiState.phoneNumber,
                medicalCondition = uiState.medicalCondition,
                isEnabled = uiState.loadState !is AddEditPatientLoadState.Loading,
                onNameChane = onNameChane,
                onAgeChange = onAgeChange,
                onGenderChange = onGenderChange,
                onPhoneNumberChange = onPhoneNumberChange,
                onMedicalConditionChange = onMedicalConditionChange
            )

            BottomContent(
                modifier = Modifier.fillMaxWidth(),
                onSaveClick = onSaveClick,
                isEnabled = isButtonEnabled,
                loading = uiState.loadState is AddEditPatientLoadState.Loading
            )
        }
    }
}

@Composable
private fun AddEditMainContent(
    modifier: Modifier = Modifier,
    name: String,
    age: Int,
    gender: Gender?,
    phoneNumber: String,
    medicalCondition: String,
    isEnabled: Boolean,
    onNameChane: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onMedicalConditionChange: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        PatientProfileHeader()
        Spacer(modifier = Modifier.height(40.dp))

        AddEditPatientForm(
            modifier = Modifier.padding(horizontal = 16.dp),
            fullName = name,
            age = age.toString(),
            gender = gender,
            phoneNumber = phoneNumber,
            medicalCondition = medicalCondition,
            isReadOnly = false,
            isEnabled = isEnabled,
            onFullNameChange = onNameChane,
            onAgeChange = onAgeChange,
            onGenderChange = onGenderChange,
            onPhoneNumberChange = onPhoneNumberChange,
            onMedicalConditionChange = onMedicalConditionChange,
        )
    }
}

@Composable
private fun BottomContent(
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    isEnabled: Boolean,
    loading: Boolean
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 20.dp
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            MyPatientsAppPrimaryButton(
                text = "Save Patient Info",
                onClick = onSaveClick,
                enabled = isEnabled,
                loading = loading
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 34,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun AddEditPatientInfoLightModePreview() {
    MyPatientsTheme {
        Surface {
            AddEditPatientScreenContent(
                uiState = AddEditPatientUiState(
                    name = "John Doe",
                    age = 30,
                    gender = Gender.Male,
                    phoneNumber = "1234567890",
                    medicalCondition = "Hypertension",
                    loadState = AddEditPatientLoadState.Idle
                ),
                isAddMode = true,
                onBackPress = {},
                onSaveClick = {},
                onNameChane = {},
                onAgeChange = {},
                onGenderChange = {},
                onPhoneNumberChange = {},
                onMedicalConditionChange = {}
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 34,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun AddEditPatientInfoDarkModePreview() {
    MyPatientsTheme {
        Surface {
            AddEditPatientScreenContent(
                uiState = AddEditPatientUiState(
                    name = "John Doe",
                    age = 30,
                    gender = Gender.Male,
                    phoneNumber = "1234567890",
                    medicalCondition = "Hypertension",
                    loadState = AddEditPatientLoadState.Idle
                ),
                isAddMode = true,
                onBackPress = {},
                onSaveClick = {},
                onNameChane = {},
                onAgeChange = {},
                onGenderChange = {},
                onPhoneNumberChange = {},
                onMedicalConditionChange = {}
            )
        }
    }
}