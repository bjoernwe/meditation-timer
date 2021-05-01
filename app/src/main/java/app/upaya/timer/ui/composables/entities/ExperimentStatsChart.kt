package app.upaya.timer.ui.composables.entities

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import app.upaya.timer.experiments.repositories.stats.ExperimentStats
import com.db.williamchart.view.BarChartView


@Composable
fun ExperimentStatsChart(experimentStats: List<ExperimentStats>, modifier: Modifier = Modifier) {

    val experimentLogs = experimentStats.mapIndexed { index, stats ->
        index.toString() to stats.count.toFloat()
    }

    val primaryColor = MaterialTheme.colors.primary

    AndroidView(
            modifier = modifier,
            factory = {
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
