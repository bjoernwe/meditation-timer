package app.upaya.timer.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import app.upaya.timer.sessions.SessionAggregate
import com.db.williamchart.view.BarChartView


@Composable
fun SessionChart(sessionAggregates: State<List<SessionAggregate>>, modifier: Modifier = Modifier) {

    val sessionEntries = sessionAggregates.value.reversed().mapIndexed { index, aggregate ->
        index.toString() to aggregate.sessionCount.toFloat()
    }

    val primaryColor = MaterialTheme.colors.primary

    AndroidView(
            modifier = modifier,
            viewBlock = {
                BarChartView(it).apply {
                    this.labelsSize = 0f
                    this.barRadius = 6f
                    this.spacing = 24f
                    this.barsColor = primaryColor.toArgb()
                }
            }
    ) {
        it.show(sessionEntries)
    }

}