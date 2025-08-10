package com.neo.mypatients.patient.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.neo.mypatients.R
import com.neo.mypatients.core.presentation.components.input.MyPatientsAppTextInputField
import com.neo.mypatients.core.utils.orEmpty
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.presentation.model.GenderSelectable

@Composable
fun AddEditPatientForm(
    modifier: Modifier = Modifier,
    isReadOnly: Boolean = false,
    isEnabled: Boolean = true,
    fullName: String?,
    age: String?,
    gender: Gender?,
    phoneNumber: String?,
    medicalCondition: String?,
    onFullNameChange: (String) -> Unit = {},
    onAgeChange: (String) -> Unit = {},
    onGenderChange: (Gender) -> Unit= {},
    onPhoneNumberChange: (String) -> Unit = {},
    onMedicalConditionChange: (String) -> Unit = {},
) {
    val genderIcon = remember {
        if (gender == Gender.Male) R.drawable.ic_male_icon else R.drawable.ic_female_icon
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputDetailItem(
            label = "Full name",
            value = fullName.orEmpty,
            onValueChange = onFullNameChange,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            readOnly = isReadOnly,
            placeHolder = "Full name",
            enableClearButton = !isReadOnly,
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_patient),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        )

        InputDetailItem(
            label = "Age",
            placeHolder = "Age",
            value = if (isReadOnly) stringResource(R.string.x_years_label, age.orEmpty) else age.orEmpty,
            onValueChange = onAgeChange,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
            readOnly = isReadOnly,
            enableClearButton = !isReadOnly,
        )

        if (isReadOnly) {
            InputDetailItem(
                label = "Gender",
                placeHolder = "Gender",
                value = gender?.name.orEmpty,
                onValueChange = {},
                readOnly = true,
                enableClearButton = false,
                trailingIcon = {
                    Icon(
                        painter = painterResource(genderIcon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            )
        } else {
            SelectableGenderItem(
                label = "Gender",
                selectedItem = gender?.let { GenderSelectable(it) },
                enabled = isEnabled,
                onItemSelected = onGenderChange
            )
        }

        InputDetailItem(
            label = "Phone number",
            value = phoneNumber.orEmpty,
            onValueChange = onPhoneNumberChange,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
            readOnly = isReadOnly,
            maxLength = 11,
            enableClearButton = !isReadOnly,
            placeHolder = "Phone number",
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_phone),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        )

        InputDetailItem(
            label = "Medical Condition",
            value = medicalCondition.orEmpty,
            onValueChange = onMedicalConditionChange,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            readOnly = isReadOnly,
            enableClearButton = !isReadOnly,
            placeHolder = "Medical Condition",
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_medical_condition),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        )
    }
}

@Composable
private fun InputDetailItem(
    modifier: Modifier = Modifier,
    label: String,
    placeHolder: String,
    value: String,
    maxLength: Int? = null,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    readOnly: Boolean = false,
    enableClearButton: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        MyPatientsAppTextInputField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            placeholder = placeHolder,
            maxLength = maxLength,
            showMaxLengthCounter = maxLength != null && !readOnly,
            enableClearButton = enableClearButton && !readOnly,
            keyboardType = keyboardType,
            imeAction = imeAction,
            trailingIcon = trailingIcon
        )
    }
}

@Composable
private fun SelectableGenderItem(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: GenderSelectable?,
    enabled: Boolean = true,
    onItemSelected: (Gender) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        GenderSelectionDropdown(
            selectedItem = selectedItem,
            onItemSelected = {
                onItemSelected(it.gender)
            },
            enabled = enabled
        )
    }
}