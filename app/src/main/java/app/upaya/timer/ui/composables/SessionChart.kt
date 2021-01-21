package app.upaya.timer.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.viewModel
import app.upaya.timer.sessions.SessionViewModel
import com.db.williamchart.view.LineChartView


@Composable
fun SessionChart(modifier: Modifier = Modifier) {

    val sessionViewModel: SessionViewModel = viewModel()
    val sessions = sessionViewModel.sessions.observeAsState()

    val sessionEntries = sessions.value?.reversed()?.mapIndexed { index, session ->
        index.toString() to session.length.toFloat()
    }

    //val gradientColor = MaterialTheme.colors.onBackground

    AndroidView(
            modifier = modifier,
            viewBlock = {
                LineChartView(it).apply {
                    this.labelsSize = 0f
                    this.lineColor = Color(255, 255, 255).toArgb()
                    this.lineThickness = 4F
                    this.smooth = true
                    //this.gradientFillColors = intArrayOf(gradientColor.toArgb(), Color.Transparent.toArgb())
                }
            }
    ) {
        if (sessionEntries != null) {
            it.animate(sessionEntries)
        }
    }

}
