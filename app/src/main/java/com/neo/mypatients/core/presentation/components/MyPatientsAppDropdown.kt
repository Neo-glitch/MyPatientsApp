package com.neo.mypatients.core.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.mypatients.R
import com.neo.mypatients.core.presentation.model.IDropdownSelectable
import com.neo.mypatients.core.utils.clearFocusClickable
import com.neo.mypatients.core.utils.orEmpty
import kotlin.toString

@Composable
fun <T : IDropdownSelectable> MyPatientsAppDropdown(
    modifier: Modifier = Modifier,
    items: List<T>?,
    selectedItem: T? = null,
    onItemSelected: (T) -> Unit,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    itemContent: @Composable (T) -> Unit = {
        Text(text = it.getLabel())
    },
    disabledContent: @Composable (T) -> Unit = {
        Text(text = it.getLabel(), color = LocalContentColor.current.copy(alpha = 0.5f))
    },
) {
    var expanded by remember { mutableStateOf(false) }
    val isSpinnerEnabled by remember(enabled, isLoading, items) {
        derivedStateOf {
            enabled && !isLoading && items.orEmpty().isNotEmpty()
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
            .sizeIn(minHeight = 56.dp)
            .alpha(if (isSpinnerEnabled) 1f else 0.5f)
            .clearFocusClickable(
                enabled = isSpinnerEnabled,
                onClick = {
                    expanded = true
                },
            ),
        contentAlignment = Alignment.Center
    ) {
        val width = maxWidth

        DropdownContent(
            modifier = modifier.fillMaxWidth(),
            label = label,
            selectedItem = selectedItem,
        )

        DropdownListContent(
            modifier = modifier.width(width),
            expanded = expanded,
            onDismiss = { expanded = false },
            items = items,
            disabledOptionIndex = null,
            onItemSelected = onItemSelected,
            itemContent = itemContent,
            disabledContent = disabledContent
        )
    }
}

@Composable
private fun <T : IDropdownSelectable> DropdownContent(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: T?,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnimatedVisibility(selectedItem != null) {
                Text(label, fontSize = 12.sp, style = MaterialTheme.typography.bodySmall)
            }

            Text(
                text = selectedItem?.getLabel() ?: label,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.alpha(if (selectedItem == null) 0.4f else 1f)
            )
        }

        Spacer(Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.ic_arrow_down),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun <T : IDropdownSelectable> DropdownListContent(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismiss: () -> Unit,
    items: List<T>?,
    disabledOptionIndex: Int?,
    onItemSelected: (T) -> Unit,
    itemContent: @Composable (T) -> Unit,
    disabledContent: @Composable (T) -> Unit,
) {
    DropdownMenu(
        containerColor = MaterialTheme.colorScheme.surface,
        expanded = expanded,
        onDismissRequest = onDismiss,
        scrollState = rememberScrollState(),
        modifier = modifier
            .heightIn(max = 280.dp)
            .background(color = MaterialTheme.colorScheme.surface),
        offset = DpOffset(0.dp, (-4).dp)
    ) {
        items?.forEachIndexed { index, item ->
            val isDisabled = index == disabledOptionIndex

            DropdownMenuItem(
                onClick = {
                    if (!isDisabled) {
                        onItemSelected(item)
                        onDismiss()
                    }
                },
                enabled = !isDisabled,
                text = {
                    when {
                        isDisabled -> disabledContent(item)
                        else -> itemContent(item)
                    }
                },
            )
        }
    }
}