package com.neo.mypatients.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


@Immutable
data class MyPatientColors(
    val warning: Color,
    val success: Color,
    val grey100: Color,
    val grey200: Color,
)

val LocalMyPatientColors = staticCompositionLocalOf {
    MyPatientColors(
        warning = Color.Unspecified,
        success = Color.Unspecified,
        grey200 = Color.Unspecified,
        grey100 = Color.Unspecified
    )
}