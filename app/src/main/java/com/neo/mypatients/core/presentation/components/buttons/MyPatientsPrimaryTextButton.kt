package com.neo.mypatients.core.presentation.components.buttons

import MyPatientsAppLoadingIndicator
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun MyPatientsAppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: ButtonIcon? = null,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    contentPadding: PaddingValues = PaddingValues(vertical = 0.dp, horizontal = 8.dp),
) {
    TextButton(
        onClick = if (enabled && !loading) onClick else ({}),
        enabled = enabled,
        shape = shape,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
            disabledContentColor = disabledContentColor
        ),
        contentPadding = contentPadding
    ) {
        if (loading) {
            MyPatientsAppLoadingIndicator()
        } else {
            when (icon) {
                is ButtonIcon.DrawableIcon -> MyPatientsAppButtonIcon(
                    iconResource = icon.resId,
                    iconSize = 20.dp
                )
                is ButtonIcon.VectorIcon -> MyPatientsAppButtonIcon(
                    iconResource = icon.imageVector,
                    iconSize = 20.dp
                )
                null -> Unit
            }
            MyPatientsAppButtonText(
                text = text,
                textStyle = textStyle,
                color = contentColor,
            )
        }
    }
}
