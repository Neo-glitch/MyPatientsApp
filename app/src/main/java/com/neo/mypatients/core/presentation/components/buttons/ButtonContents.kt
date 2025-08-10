package com.neo.mypatients.core.presentation.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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

@Composable
fun MyPatientsAppButtonIcon(iconResource: ImageVector, iconSize: Dp) {
    Box(modifier = Modifier.padding(end = 4.dp)) {
        Icon(
            painter = rememberVectorPainter(iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(iconSize)
                .testTag("button_icon")
        )
    }
}

@Composable
fun MyPatientsAppButtonIcon(@DrawableRes iconResource: Int, iconSize: Dp) {
    Box(modifier = Modifier.padding(end = 4.dp)) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(iconSize)
                .testTag("button_icon")
        )
    }
}

@Composable
fun MyPatientsAppButtonText(text: String, textStyle: TextStyle, color: Color) {
    Text(
        text = text,
        style = textStyle,
        color = color,
        textAlign = TextAlign.Center,
        modifier = Modifier.testTag("button_text")
    )
}