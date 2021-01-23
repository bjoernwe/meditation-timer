package app.upaya.timer.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.viewModel
import app.upaya.timer.sessions.SessionViewModel
import com.db.williamchart.view.BarChartView


@Composable
fun SessionChart(modifier: Modifier = Modifier) {

    val sessionViewModel: SessionViewModel = viewModel()
    val sessionAggregates = sessionViewModel.sessionAggOfLastDays.observeAsState()

    val sessionEntries = sessionAggregates.value?.reversed()?.mapIndexed { index, aggregate ->
        index.toString() to aggregate.session_count.toFloat()
    }

    val primaryColor = MaterialTheme.colors.primary

    AndroidView(
            modifier = modifier,
            viewBlock = {
                BarChartView(it).apply {
                    this.labelsSize = 0f
                    this.barRadius = 8f
                    this.spacing = 24f
                    this.barsColor = primaryColor.toArgb()
                }
            }
    ) {
        if (sessionEntries != null) {
            it.show(sessionEntries)
        }
    }

}
