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
    val sessionCount = sessionViewModel.sessionCount.observeAsState(initial = 0)
    val sessionTotal = sessionViewModel.sessionTotal.observeAsState(initial = 0)

    Column(Modifier.padding(16.dp)) {

        SessionChart(
                Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(8.dp)
        )

        Column(Modifier.padding(8.dp)) {

            Text(
                    text = "${sessionCount.value} sessions "
                            + "(${fromSecsToTimeString(sessionTotal.value)} total)",
                    color = MaterialTheme.colors.onSurface,
            )

        }

    }

}
