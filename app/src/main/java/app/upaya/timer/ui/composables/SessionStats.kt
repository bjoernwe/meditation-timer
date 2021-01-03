package app.upaya.timer.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.upaya.timer.timer.TimerViewModel
import app.upaya.timer.ui.fromSecsToTimeString


@Composable
fun SessionStats(timerViewModel: TimerViewModel) {

    val sessionAvg = timerViewModel.sessionAvg.observeAsState(initial = 0f)
    val sessionCount = timerViewModel.sessionCount.observeAsState(initial = 0)

    Column(Modifier.padding(16.dp)) {

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