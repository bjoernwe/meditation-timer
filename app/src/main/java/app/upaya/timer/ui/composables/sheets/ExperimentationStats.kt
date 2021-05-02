package app.upaya.timer.ui.composables.sheets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.upaya.timer.experiments.repositories.stats.ExperimentStats
import app.upaya.timer.experiments.viewmodel.ExperimentViewModel
import app.upaya.timer.experiments.viewmodel.ExperimentViewModelFactory
import app.upaya.timer.ui.composables.entities.ExperimentStatsChart
import app.upaya.timer.ui.fromSecsToTimeString


@Composable
fun ExperimentationStats() {

    val experimentViewModel: ExperimentViewModel = viewModel(factory = ExperimentViewModelFactory(LocalContext.current))
    val experimentLength by experimentViewModel.experimentLength.observeAsState(initial = 0.0)
    val experimentStats by experimentViewModel.experimentStats.observeAsState(ExperimentStats())
    val experimentStatsOfLastDays by experimentViewModel.experimentStatsOfLastDays.observeAsState(listOf())

    Column(Modifier.padding(16.dp)) {

        if (experimentStatsOfLastDays.size >= 3) {
            ExperimentStatsChart(
                    experimentStats = experimentStatsOfLastDays,
                    modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .height(150.dp)
                            .padding(8.dp)
                            .padding(top = 16.dp)
            )
        } else {
            Text(
                    text = "not enough experimentation days yet",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .padding(8.dp),
            )
        }

        Column(Modifier.padding(8.dp)) {

            Divider(Modifier.padding(bottom = 24.dp))

            Text(
                    text = "${experimentStats.count} experiments "
                            + "(${fromSecsToTimeString(experimentStats.totalLength)} total)",
                    color = MaterialTheme.colors.onSurface,
            )

            Text(
                    text = "Current experiment length: ${experimentLength.toInt()}",
                    color = MaterialTheme.colors.onSurface,
            )

        }

    }

}
