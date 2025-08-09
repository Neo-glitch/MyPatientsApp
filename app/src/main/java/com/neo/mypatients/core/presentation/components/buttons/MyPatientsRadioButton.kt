package com.neo.mypatients.core.presentation.components.buttons

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.neo.mypatients.ui.theme.MyPatientsAppTheme

@Composable
fun SabiRadio(
    selected: Boolean,
    modifier: Modifier = Modifier,
    colors: MyPatientsAppRadioColors = MyPatientsAppRadioColors.default(),
    onClick: (() -> Unit)?,
) {
    val selectableModifier = if (onClick != null) {
        Modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = true,
                role = Role.RadioButton,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = 24.dp)
            )
    } else Modifier

    Crossfade(targetState = selected) {
        Box(
            modifier = selectableModifier
                .size(48.dp)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            if (it) RadioChecked(colors)
            else RadioUnchecked()
        }
    }
}

@Composable
private fun RadioChecked(color: MyPatientsAppRadioColors) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .border(width = 1.5.dp, color = color.outerColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color.innerColor)
        )
    }
}

@Composable
private fun RadioUnchecked() {
    Box(
        modifier = Modifier
            .size(20.dp)
            .border(
                width = 1.5.dp,
                color = MyPatientsAppTheme.colors.grey100Disabled,
                shape = CircleShape
            )
    )
}

data class MyPatientsAppRadioColors(
    val outerColor: Color,
    val innerColor: Color,
) {
    companion object {
        @Composable
        fun default() = MyPatientsAppRadioColors(
            outerColor = MaterialTheme.colorScheme.primary,
            innerColor = MaterialTheme.colorScheme.primary
        )
    }
}