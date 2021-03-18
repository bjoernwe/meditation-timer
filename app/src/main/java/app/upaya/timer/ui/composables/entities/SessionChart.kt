package app.upaya.timer.ui.composables.entities

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import app.upaya.timer.session.repository.stats.SessionStats
import com.db.williamchart.view.BarChartView


@Composable
fun SessionChart(sessionStats: State<List<SessionStats>>, modifier: Modifier = Modifier) {

    val sessionLogs = sessionStats.value.reversed().mapIndexed { index, stats ->
        index.toString() to stats.sessionCount.toFloat()
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
        it.show(sessionLogs)
    }

}
