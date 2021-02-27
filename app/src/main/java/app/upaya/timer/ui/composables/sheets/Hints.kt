package app.upaya.timer.ui.composables.sheets

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Science
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import app.upaya.timer.R
import app.upaya.timer.ui.composables.blueGrey800Light


@Composable
fun SessionHintsCard(modifier: Modifier = Modifier) {

    Row(
            modifier = modifier,
            verticalAlignment = Alignment.Top,
    ) {

        Icon(
                Icons.Default.Science,
                tint = blueGrey800Light,
                modifier = Modifier.padding(8.dp)
        )

        Text(
                text = stringArrayResource(R.array.hints).random(),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
        )

    }

}


@Preview
@Composable
fun SessionHintsPreview() {
    SessionHintsCard()
}
