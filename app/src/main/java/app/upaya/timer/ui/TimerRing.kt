package app.upaya.timer.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRippleIndication
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TimerRing(isRunning: State<Boolean>, secondsLeft: State<String>, onClick: () -> Unit) {

    // Ripple effect when clicking ring
    val rippleIndication = rememberRippleIndication(
            radius = 0.dp,
            bounded = false,
            color = Color.Transparent
    )

    Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxSize()
                    .clickable(
                            onClick = onClick,
                            indication = rippleIndication,
                    )
    ) {

        // Ring
        MaterialRing(
            size = 200.dp,
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
