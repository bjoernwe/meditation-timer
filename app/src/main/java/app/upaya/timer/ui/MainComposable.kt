package app.upaya.timer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State


@Composable
fun MainComposable(isRunning: State<Boolean?>, secondsLeft: State<String?>, onClick: () -> Unit) {

    TimerTheme {
        TimerRing(isRunning = isRunning, secondsLeft = secondsLeft, onClick = onClick)
    }

}
