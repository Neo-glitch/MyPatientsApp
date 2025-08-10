package com.neo.mypatients.core.presentation.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.neo.mypatients.R
import com.neo.mypatients.core.presentation.components.buttons.MyPatientsAppTextButton
import com.neo.mypatients.core.presentation.components.divider.MyPatientsAppHorizontalDivider
import com.neo.mypatients.core.presentation.components.divider.MyPatientsAppVerticalDivider
import com.neo.mypatients.ui.theme.MyPatientsAppTheme
import com.neo.mypatients.ui.theme.MyPatientsTheme

@Composable
fun DualButtonAlertDialog(
    isCancellable: Boolean = false,
    onDismiss: () -> Unit = {},
    title: String,
    content: String,
    onPositiveButtonClick: () -> Unit = {},
    onNegativeButtonClick: () -> Unit = {},
    positiveButtonText: String = stringResource(R.string.continue_label),
    negativeButtonText: String = stringResource(R.string.cancel_label),
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = isCancellable,
            dismissOnClickOutside = isCancellable
        )
    ) {
        DualButtonAlertDialogContent(
            title = title,
            content = content,
            onPositiveButtonClick = onPositiveButtonClick,
            onNegativeButtonClick = onNegativeButtonClick,
            positiveButtonText = positiveButtonText,
            negativeButtonText = negativeButtonText
        )
    }
}

@Composable
private fun DualButtonAlertDialogContent(
    title: String,
    content: String,
    onPositiveButtonClick: () -> Unit = {},
    onNegativeButtonClick: () -> Unit = {},
    positiveButtonText: String,
    negativeButtonText: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(title, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 8.dp))

            Spacer(modifier = Modifier.height(12.dp))
            Text(content, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 8.dp))

            Spacer(modifier = Modifier.height(28.dp))
            MyPatientsAppHorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
            ) {
                MyPatientsAppTextButton(
                    text = negativeButtonText,
                    onClick = onNegativeButtonClick,
                    contentColor = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.weight(1f).padding(vertical = 8.dp)
                )
                MyPatientsAppVerticalDivider()
                MyPatientsAppTextButton(
                    text = positiveButtonText,
                    onClick = onPositiveButtonClick,
                    modifier = Modifier.weight(1f).padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun DualButtonAlertDialogPreview() {
    MyPatientsTheme {
        DualButtonAlertDialogContent(
            title = "Title",
            content = "Content",
            onPositiveButtonClick = {},
            onNegativeButtonClick = {},
            positiveButtonText = "Positive",
            negativeButtonText = "Negative"
        )
    }
}