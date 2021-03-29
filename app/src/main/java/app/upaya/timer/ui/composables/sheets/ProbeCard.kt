package app.upaya.timer.ui.composables.sheets

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Science
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import app.upaya.timer.experiments.viewmodel.ExperimentViewModel
import app.upaya.timer.ui.composables.blueGrey800Light


@Composable
fun ProbeCard(modifier: Modifier = Modifier) {

    val experimentViewModel: ExperimentViewModel = viewModel()
    val probe by experimentViewModel.currentProbe.observeAsState()

    Row(
            modifier = modifier,
            verticalAlignment = Alignment.Top,
    ) {

        Icon(
                Icons.Default.Science,
                tint = blueGrey800Light,
                modifier = Modifier.padding(8.dp)
        )

        probe?.probe?.let { probe ->

            Text(
                text = probe,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

        }

    }

}


@Preview
@Composable
fun ProbeCardPreview() {
    ProbeCard()
}
