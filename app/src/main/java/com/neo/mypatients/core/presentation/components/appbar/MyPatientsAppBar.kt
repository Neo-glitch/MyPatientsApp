package com.neo.mypatients.core.presentation.components.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPatientsAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    navigationAction: AppBarAction,
    actions: List<AppBarAction> = emptyList(),
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            title?.let {
                Text(it, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.titleLarge)
            }
        },
        navigationIcon = {
            IconButton(onClick = navigationAction.onClick) {
                Icon(
                    painter = painterResource(navigationAction.icon),
                    contentDescription = navigationAction.contentDescription,
                    tint = navigationAction.tint
                )
            }
        },
        actions = {
            actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(
                        painter = painterResource(action.icon),
                        contentDescription = action.contentDescription,
                        tint = action.tint
                    )
                }
            }
        }
    )
}

data class AppBarAction(
    val icon: Int,
    val onClick: () -> Unit,
    val tint: Color = Color.Unspecified,
    val contentDescription: String? = null
)