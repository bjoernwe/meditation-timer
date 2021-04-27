package app.upaya.timer.ui.composables.sheets

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import app.upaya.timer.experiments.repositories.stats.ExperimentStats
import app.upaya.timer.experiments.viewmodel.ExperimentViewModel
import app.upaya.timer.experiments.viewmodel.ExperimentViewModelFactory
import app.upaya.timer.ui.composables.entities.ExperimentStatsChart
import app.upaya.timer.ui.fromSecsToTimeString
import app.upaya.timer.ui.toCSV


@Composable
fun ExperimentStatsSheet() {

    val currentContext = LocalContext.current
    val experimentViewModel: ExperimentViewModel = viewModel(factory = ExperimentViewModelFactory(currentContext))
    val experimentLength = experimentViewModel.experimentLength.observeAsState(initial = 0.0)
    val experimentStats = experimentViewModel.experimentStats.observeAsState(ExperimentStats())
    val experimentStatsOfLastDays = experimentViewModel.experimentStatsOfLastDays.observeAsState(listOf())
    val experiments by experimentViewModel.experiments.observeAsState(ArrayList())

    Column(Modifier.padding(16.dp)) {

        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/csv"
                    putExtra(Intent.EXTRA_TEXT, experiments.toCSV())
                }
                val shareIntent = Intent.createChooser(sendIntent, "Share CSV data")
                currentContext.startActivity(shareIntent)
            }
        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "Share CSV data")
        }

        if (experimentStatsOfLastDays.value.size >= 3) {
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
                    text = "${experimentStats.value.count} experiments "
                            + "(${fromSecsToTimeString(experimentStats.value.totalLength)} total)",
                    color = MaterialTheme.colors.onSurface,
            )

            Text(
                    text = "Current experiment length: ${experimentLength.value.toInt()}",
                    color = MaterialTheme.colors.onSurface,
            )

        }

    }

}
