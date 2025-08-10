package com.neo.mypatients.core.presentation.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.neo.mypatients.core.presentation.components.buttons.MyPatientsAppTextButton
import com.neo.mypatients.core.presentation.components.divider.MyPatientsAppHorizontalDivider

@Composable
fun SingleButtonAlertDialog(
    isCancellable: Boolean = false,
    onDismiss: () -> Unit = {},
    onButtonClick: () -> Unit = {},
    title: String,
    content: String
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = isCancellable,
            dismissOnClickOutside = isCancellable
        )
    ) {
        SingleButtonAlertDialogContent(
            title = title,
            content = content,
            onButtonClick = onButtonClick
        )
    }
}

@Composable
private fun SingleButtonAlertDialogContent(
    title: String,
    content: String,
    onButtonClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(title, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(horizontal = 8.dp), textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(12.dp))
            Text(content, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 8.dp), textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(28.dp))
            MyPatientsAppHorizontalDivider()
            Spacer(modifier = Modifier.height(28.dp))
            MyPatientsAppTextButton(text = "Continue", onClick = onButtonClick)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}