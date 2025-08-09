package com.neo.mypatients.core.presentation.components.input

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.neo.mypatients.R
import kotlinx.coroutines.delay

@Composable
fun MyPatientsAppSearchInputField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
) {

    var text by remember { mutableStateOf(value) }

    MyPatientsAppTextInputField(
        value = value,
        onValueChange = { newText ->
            text = newText
        },
        modifier = modifier,
        placeholder = placeholder,
        leadingIcon = {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }
    )

    LaunchedEffect(text) {
        delay(300)
        onValueChange(text)
    }
}