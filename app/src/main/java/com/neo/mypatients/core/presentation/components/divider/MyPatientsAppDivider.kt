package com.neo.mypatients.core.presentation.components.divider

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MyPatientsAppHorizontalDivider(modifier: Modifier = Modifier, height: Dp = 1.dp) {
    HorizontalDivider(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        thickness = height,
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
fun MyPatientsAppVerticalDivider(modifier: Modifier = Modifier, width: Dp = 1.dp) {
    VerticalDivider(
        modifier = modifier
            .fillMaxHeight()
            .width(width),
        thickness = width,
        color = MaterialTheme.colorScheme.outline
    )

}