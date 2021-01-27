package app.upaya.timer.ui.composables.sheets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import app.upaya.timer.ui.composables.blueGrey800Dark


@Composable
fun SessionHintsCard(modifier: Modifier = Modifier) {

    Card(
            backgroundColor = blueGrey800Dark,
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
            modifier = modifier
    ) {

        Text(
                text = "Notice your breath wherever you can sense it.",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(20.dp, 14.dp, 20.dp, 16.dp)
        )

    }

}


@Preview
@Composable
fun SessionHintsPreview() {
    SessionHintsCard()
}
