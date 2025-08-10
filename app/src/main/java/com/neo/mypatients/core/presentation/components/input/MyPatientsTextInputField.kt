package com.neo.mypatients.core.presentation.components.input

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.neo.mypatients.R

@Composable
fun MyPatientsAppTextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    label: String? = null,
    enabled: Boolean = true,
    placeholder: String? = null,
    maxLength: Int? = null,
    showMaxLengthCounter: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    error: String? = null,
    showError: Boolean = false,
    textFieldColors: TextFieldColors = myPatientAppTextInputColors,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Default,
    onDoneClicked: () -> Unit = {},
    onFocusChange: ((Boolean) -> Unit)? = null,
    autoFocus: Boolean = false,
    readOnly: Boolean = false,
    enableClearButton: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val showClearButton = remember(value, isFocused) {
        value.isNotBlank() && isFocused && enableClearButton
    }
    val isFocusedOrValueNotBlank = remember(value, isFocused) {
        isFocused || value.isNotBlank()
    }

    LaunchedEffect(autoFocus) {
        if (autoFocus) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Column( modifier = modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = { newText ->
                maxLength?.let {
                    if (newText.length <= it) onValueChange(newText)
                } ?: onValueChange(newText)
            },
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChange?.invoke(it.isFocused)
                }
                .fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            colors = textFieldColors,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = capitalization,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onDoneClicked()
                }
            ),
            interactionSource = interactionSource,
            label = label?.let {
                {
                    Text(text = it, style = LocalTextStyle.current)
                }
            },
            placeholder = placeholder?.let {
                {
                    Text(text = it)
                }
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon ?: if (showClearButton) {
                {
                    MyPatientsAppClearButton (
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        onClick = { onValueChange("") }
                    )
                }
            } else null,
            isError = showError,
            shape = RoundedCornerShape(8.dp),
            supportingText = if (showError) {
                {
                    MyPatientAppInputError(text = error ?: "")
                }
            } else null
        )
        if (showMaxLengthCounter) {
            maxLength?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    MyPatientAppInputCounter(length = value.length, maxLength = it)
                }
            }
        }
    }
}



