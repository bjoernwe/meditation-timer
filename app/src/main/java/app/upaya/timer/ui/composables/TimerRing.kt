package app.upaya.timer.ui.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRippleIndication
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.upaya.timer.timer.TimerViewModel
import app.upaya.timer.ui.teal200


@Composable
fun TimerRing(timerViewModel: TimerViewModel, onClick: () -> Unit) {

    val isRunning = timerViewModel.isRunning.observeAsState(false)
    val secondsLeft = timerViewModel.secondsLeftString.observeAsState("")

    // Ripple effect when clicking ring
    val rippleIndication = rememberRippleIndication(
        bounded = false,
        color = Color.Transparent
    )

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .clickable(
                onClick = onClick,
                enabled = !isRunning.value,
                indication = rippleIndication,
            )
    ) {

        // Ring
        MaterialRing(
            size = 180.dp,
            thickness = 16.dp,
            depth = 3.dp,
            color = if (isRunning.value) Color.White else teal200
        )

        // Text
        if (isRunning.value) {
            ShadowText(text = secondsLeft.value)
        }

    }

}
