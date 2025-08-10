package com.neo.mypatients.core.presentation.components.buttons

import MyPatientsAppLoadingIndicator
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.neo.mypatients.core.presentation.components.buttons.MyPatientsAppButtonIcon

@Composable
fun MyPatientsAppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: ButtonIcon? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    contentPadding: PaddingValues = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
    border: BorderStroke? = null,
) {
    Button(
        onClick = if (enabled && !loading) onClick else ({}),
        enabled = enabled,
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .height(48.dp),
        colors = buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.5f),
        ),
        contentPadding = contentPadding,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
        ),
        border = border,
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
                textStyle = textStyle,
                color = contentColor,
            )
        }
    }
}



sealed interface ButtonIcon {
    data class VectorIcon(val imageVector: ImageVector) : ButtonIcon
    data class DrawableIcon(@DrawableRes val resId: Int) : ButtonIcon
}