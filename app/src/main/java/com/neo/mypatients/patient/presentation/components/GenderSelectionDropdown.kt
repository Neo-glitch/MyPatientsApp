package com.neo.mypatients.patient.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.neo.mypatients.core.presentation.components.MyPatientsAppDropdown
import com.neo.mypatients.patient.presentation.model.GenderSelectable

@Composable
fun GenderSelectionDropdown(
    modifier: Modifier = Modifier,
    selectedItem: GenderSelectable?,
    onItemSelected: (GenderSelectable) -> Unit,
    enabled: Boolean = true,
) {
    val items = remember { GenderSelectable.GENDERS }

    MyPatientsAppDropdown(
        modifier = modifier,
        items = items,
        selectedItem = selectedItem,
        onItemSelected = onItemSelected,
        label = "Gender",
        enabled = enabled,
    )
}