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
import app.upaya.timer.sessions.SessionViewModel
import app.upaya.timer.sessions.SessionViewModelFactory
import app.upaya.timer.timer.TimerViewModel
import app.upaya.timer.timer.TimerViewModelFactory
import app.upaya.timer.ui.fromSecsToTimeString


@Composable
fun SessionStats() {

    val sessionViewModel: SessionViewModel = viewModel(factory = SessionViewModelFactory(AmbientContext.current))
    val timerViewModel: TimerViewModel = viewModel(factory = TimerViewModelFactory(AmbientContext.current))
    val sessionCount = sessionViewModel.sessionCount.observeAsState(initial = 0)
    val sessionLength = timerViewModel.sessionLength.observeAsState(initial = 0.0)
    val sessionTotal = sessionViewModel.sessionTotal.observeAsState(initial = 0)

    Column(Modifier.padding(16.dp)) {

        if (sessionCount.value >= 3) {
            SessionChart(
                    Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .height(150.dp)
                            .padding(8.dp)
                            .padding(top = 16.dp)
            )
        } else {
            Text(
                    text = "not enough sessions yet",
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
                    text = "${sessionCount.value} sessions "
                            + "(${fromSecsToTimeString(sessionTotal.value)} total)",
                    color = MaterialTheme.colors.onSurface,
            )

            Text(
                    text = "Current session length: ${sessionLength.value.toInt()}",
                    color = MaterialTheme.colors.onSurface,
            )

        }

    }

}
