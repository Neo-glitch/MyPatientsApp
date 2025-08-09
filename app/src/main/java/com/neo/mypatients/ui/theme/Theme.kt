package com.neo.mypatients.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import md_theme_dark_background
import md_theme_dark_error
import md_theme_dark_errorContainer
import md_theme_dark_onBackground
import md_theme_dark_onError
import md_theme_dark_onErrorContainer
import md_theme_dark_onPrimary
import md_theme_dark_onPrimaryContainer
import md_theme_dark_onSecondary
import md_theme_dark_onSecondaryContainer
import md_theme_dark_onSurface
import md_theme_dark_onSurfaceVariant
import md_theme_dark_outline
import md_theme_dark_primary
import md_theme_dark_primaryContainer
import md_theme_dark_secondary
import md_theme_dark_secondaryContainer
import md_theme_dark_success
import md_theme_dark_surface
import md_theme_dark_surfaceVariant
import md_theme_dark_warning
import md_theme_light_background
import md_theme_light_error
import md_theme_light_errorContainer
import md_theme_light_onBackground
import md_theme_light_onError
import md_theme_light_onErrorContainer
import md_theme_light_onPrimary
import md_theme_light_onPrimaryContainer
import md_theme_light_onSecondary
import md_theme_light_onSecondaryContainer
import md_theme_light_onSurface
import md_theme_light_onSurfaceVariant
import md_theme_light_outline
import md_theme_light_primary
import md_theme_light_primaryContainer
import md_theme_light_secondary
import md_theme_light_secondaryContainer
import md_theme_light_success
import md_theme_light_surface
import md_theme_light_surfaceVariant
import md_theme_light_warning

val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,

    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,

    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,

    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,

    outline = md_theme_light_outline,

    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer
)


val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,

    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,

    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,

    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,

    outline = md_theme_dark_outline,

    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer
)

@Composable
fun MyPatientsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val extendedTheme = getMyPatientColors(darkTheme)

    CompositionLocalProvider(LocalMyPatientColors provides extendedTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

@Composable
fun getMyPatientColors(darkTheme: Boolean = isSystemInDarkTheme()): MyPatientColors {
    return if (darkTheme)
        MyPatientColors(
            warning = md_theme_light_warning,
            success = md_theme_light_success,
            grey100Disabled = Color(0xFF999999)
        )
    else
        MyPatientColors(
            warning = md_theme_dark_warning,
            success =md_theme_dark_success,
            grey100Disabled = Color(0xFF999999)
        )
}

object MyPatientsAppTheme {
    val colors: MyPatientColors
        @Composable
        get() = LocalMyPatientColors.current
}