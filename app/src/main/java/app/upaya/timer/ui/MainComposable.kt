package app.upaya.timer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.ui.tooling.preview.Preview


@Composable
fun MainComposable(isRunning: State<Boolean>, secondsLeft: State<String>, onClick: () -> Unit) {

    TimerTheme {
        TimerRing(isRunning = isRunning, secondsLeft = secondsLeft, onClick = onClick)
    }

}


@Preview
@Composable
fun MainComposablePreview() {
    MainComposable(
            isRunning = mutableStateOf(false),
            secondsLeft = mutableStateOf("0:00:00"),
            onClick = { }
    )
}
