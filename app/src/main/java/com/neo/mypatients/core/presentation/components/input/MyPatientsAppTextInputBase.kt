package com.neo.mypatients.core.presentation.components.input

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.neo.mypatients.R

val myPatientAppTextInputColors: TextFieldColors
    @Composable
    get() = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,

        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,

        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        errorTextColor = MaterialTheme.colorScheme.onErrorContainer,

        cursorColor = MaterialTheme.colorScheme.primary,
        errorCursorColor = MaterialTheme.colorScheme.error,

        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        errorPlaceholderColor = MaterialTheme.colorScheme.onErrorContainer
    )

val myPatientAppOutlinedTextColors: TextFieldColors
@Composable
get() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    errorContainerColor = Color.Transparent,

    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
    errorBorderColor = MaterialTheme.colorScheme.error,

    focusedTextColor = MaterialTheme.colorScheme.onSurface,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
    disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
    errorTextColor = MaterialTheme.colorScheme.error,

    cursorColor = MaterialTheme.colorScheme.primary,
    errorCursorColor = MaterialTheme.colorScheme.error,

    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
    errorPlaceholderColor = MaterialTheme.colorScheme.error
)

@Composable
fun MyPatientAppInputError(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(top = 8.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onError,
        )
    }
}

@Composable
fun MyPatientAppInputCounter(modifier: Modifier = Modifier, length: Int, maxLength: Int) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .then(modifier)
    ) {
        Text(text = "$length / $maxLength", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun MyPatientsAppClearButton(
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier,
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(R.drawable.ic_close),
            contentDescription = null,
            colorFilter = colorFilter,
        )
    }
}