import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MyPatientsAppLoadingIndicator(modifier: Modifier = Modifier, size: Dp = 16.dp) {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.secondary,
        strokeWidth = 2.dp,
        modifier = Modifier
            .size(16.dp)
    )
}