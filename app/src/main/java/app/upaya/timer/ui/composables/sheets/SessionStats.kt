package app.upaya.timer.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import app.upaya.timer.session.repository.stats.SessionAggregate
import app.upaya.timer.session.viewmodel.SessionViewModel
import app.upaya.timer.session.viewmodel.SessionViewModelFactory
import app.upaya.timer.ui.composables.entities.SessionChart
import app.upaya.timer.ui.fromSecsToTimeString
import java.util.*


@Composable
fun SessionStats() {

    val sessionViewModel: SessionViewModel = viewModel(factory = SessionViewModelFactory(AmbientContext.current))
    val sessionLength = sessionViewModel.sessionLength.observeAsState(initial = 0.0)
    val sessionAggOfAll = sessionViewModel.sessionAggregate.observeAsState(SessionAggregate())
    val sessionAggOfLastDays = sessionViewModel.sessionAggregatesOfLastDays.observeAsState(listOf())

    Column(Modifier.padding(16.dp)) {

        if (sessionAggOfLastDays.value.size >= 3) {
            SessionChart(
                    sessionAggregates = sessionAggOfLastDays,
                    modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .height(150.dp)
                            .padding(8.dp)
                            .padding(top = 16.dp)
            )
        } else {
            Text(
                    text = "not enough session days yet",
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
                    text = "${sessionAggOfAll.value.sessionCount} sessions "
                            + "(${fromSecsToTimeString(sessionAggOfAll.value.totalLength)} total)",
                    color = MaterialTheme.colors.onSurface,
            )

            Text(
                    text = "Current session length: ${sessionLength.value.toInt()}",
                    color = MaterialTheme.colors.onSurface,
            )

        }

    }

}
