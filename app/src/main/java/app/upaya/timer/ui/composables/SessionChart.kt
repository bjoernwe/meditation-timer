package app.upaya.timer.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.viewModel
import app.upaya.timer.sessions.SessionViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun SessionChart(modifier: Modifier = Modifier) {

    val sessionViewModel: SessionViewModel = viewModel()
    val sessions = sessionViewModel.sessions.observeAsState()

    val sessionEntries = sessions.value?.reversed()?.mapIndexed { index, session -> Entry(index.toFloat(), session.length.toFloat()) }
    val lineDataSet = LineDataSet(sessionEntries,"label")
    val lineData = LineData(listOf(lineDataSet))

    AndroidView(
            modifier = modifier,
            viewBlock = { LineChart(it) }
    ) {
        it.data = lineData
        it.invalidate()
    }

}
