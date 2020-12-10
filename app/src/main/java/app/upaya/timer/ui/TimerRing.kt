package app.upaya.timer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TimerRing(isRunning: State<Boolean?>, secondsLeft: State<String?>, onClick: () -> Unit) {

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        // Ring
        MaterialRing(
                size = 200.dp,
                thickness = 16.dp,
                depth = 3.dp,
                color = if (isRunning.value == true) Color.White else teal200
        )

        // Text
        if (isRunning.value == true) {
            secondsLeft.value?.let { ShadowText(text = it) }
        }

    }

}