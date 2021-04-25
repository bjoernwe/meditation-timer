package app.upaya.timer.ui.composables.entities

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun StatsButton(onClick: () -> Unit, modifier: Modifier = Modifier) {

    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Assessment,
            contentDescription = "Statistics",
            tint = MaterialTheme.colors.onBackground
        )
    }

}
