package app.upaya.timer.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview


@Composable
fun SessionRatingDialog(onClickDown: () -> Unit, onClickUp: () -> Unit) {

    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
    ) {

        Text(
                "Did you lose your focus?",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(16.dp)
        )

        Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(.8f),
        ) {

            IconButton(onClick = onClickDown) {
                Icon(Icons.Default.ThumbDown, tint = MaterialTheme.colors.onBackground)
            }

            IconButton(onClick = onClickUp) {
                Icon(Icons.Default.ThumbUp, tint = MaterialTheme.colors.onBackground)
            }

        }

    }

}


@Preview
@Composable
fun SessionRatingDialogPreview() {
    SessionRatingDialog(
            onClickDown = {},
            onClickUp = {},
    )
}