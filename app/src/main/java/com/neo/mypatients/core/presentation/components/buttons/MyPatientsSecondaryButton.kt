package com.neo.mypatients.core.presentation.components.buttons

import MyPatientsAppLoadingIndicator
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyPatientsAppSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: ButtonIcon? = null,
    contentColor: Color = MaterialTheme.colorScheme.primary,
) {
    OutlinedButton(
        onClick = if (enabled && !loading) onClick else ({}),
        enabled = enabled,
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .height(48.dp),
        colors = outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = contentColor.copy(alpha = 0.5f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) contentColor else contentColor.copy(alpha = 0.5f),
        ),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp)
    ) {
        if (loading) {
            MyPatientsAppLoadingIndicator()
        } else {
            when (icon) {
                is ButtonIcon.DrawableIcon -> MyPatientsAppButtonIcon(iconResource = icon.resId, iconSize = 20.dp)
                is ButtonIcon.VectorIcon -> MyPatientsAppButtonIcon(iconResource = icon.imageVector, iconSize = 20.dp)
                null -> Unit
            }
            MyPatientsAppButtonText(
                text = text,
                textStyle = MaterialTheme.typography.labelLarge,
                color = if (enabled) contentColor else contentColor.copy(alpha = 0.5f),
            )
        }
    }
}