package com.neo.mypatients.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


@Immutable
data class MyPatientColors(
    val warning: Color,
    val success: Color,
)

val LocalMyPatientColors = staticCompositionLocalOf {
    MyPatientColors(
        warning = Color.Unspecified,
        success = Color.Unspecified,
    )
}