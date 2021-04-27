package app.upaya.timer.ui.composables.sheets

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
fun ExperimentFeedbackDialog(onClickDown: () -> Unit, onClickUp: () -> Unit) {

    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
    ) {

        Text(
                "Did you go away?",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(16.dp)
        )

        Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
        ) {

            IconButton(onClick = onClickDown) {
                Icon(
                    Icons.Default.ThumbDown,
                    contentDescription = "Thumb Down",
                    tint = MaterialTheme.colors.onBackground
                )
            }

            IconButton(onClick = onClickUp) {
                Icon(
                    Icons.Default.ThumbUp,
                    contentDescription = "Thumb Up",
                    tint = MaterialTheme.colors.onBackground
                )
            }

        }

    }

}


@Preview
@Composable
fun ExperimentFeedbackDialogPreview() {
    ExperimentFeedbackDialog(
            onClickDown = {},
            onClickUp = {},
    )
}
