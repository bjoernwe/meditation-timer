package app.upaya.timer.ui.composables.entities

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import app.upaya.timer.experiments.repository.stats.ExperimentStats
import com.db.williamchart.view.BarChartView


@Composable
fun ExperimentStatsChart(experimentStats: State<List<ExperimentStats>>, modifier: Modifier = Modifier) {

    val experimentLogs = experimentStats.value.reversed().mapIndexed { index, stats ->
        index.toString() to stats.count.toFloat()
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
        it.show(experimentLogs)
    }

}
