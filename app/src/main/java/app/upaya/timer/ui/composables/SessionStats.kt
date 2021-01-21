package app.upaya.timer.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import app.upaya.timer.sessions.SessionViewModel
import app.upaya.timer.sessions.SessionViewModelFactory
import app.upaya.timer.ui.fromSecsToTimeString


@Composable
fun SessionStats() {

    val sessionViewModel: SessionViewModel = viewModel(factory = SessionViewModelFactory(AmbientContext.current))
    val sessionAvg = sessionViewModel.sessionAvg.observeAsState(initial = 0f)
    val sessionCount = sessionViewModel.sessionCount.observeAsState(initial = 0)

    Column(Modifier.padding(16.dp)) {

        SessionChart(
                Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(8.dp)
        )

        Column(Modifier.padding(8.dp)) {

            Text(
                    text = "Number of sessions: ${sessionCount.value}",
                    color = MaterialTheme.colors.onSurface,
            )

            Text(
                    text = "Avg. session length: ${fromSecsToTimeString(sessionAvg.value.toInt())}",
                    color = MaterialTheme.colors.onSurface,
            )

        }

    }

}
