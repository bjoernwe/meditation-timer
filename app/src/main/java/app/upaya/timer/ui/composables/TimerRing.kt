package app.upaya.timer.ui.composables

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
import androidx.ui.tooling.preview.Preview


@Composable
fun TimerRing(activated: State<Boolean>, text: State<String>, onClick: () -> Unit) {

    // Ripple effect when clicking ring
    val rippleIndication = rememberRippleIndication(
        bounded = false,
        color = Color.Black
    )

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .clickable(
                onClick = onClick,
                enabled = !activated.value,
                indication = rippleIndication,
            )
    ) {

        // Ring
        MaterialRing(
            size = 180.dp,
            thickness = 12.dp,
            depth = 3.dp,
            color = if (activated.value) Color.White else MaterialTheme.colors.primary
        )

        // Text
        if (activated.value) {
            ShadowText(text = text.value)
        }

    }

}


@Preview
@Composable
fun TimerRingPreview() {
    TimerRing(
            activated = mutableStateOf(false),
            text = mutableStateOf("0:00:00"),
            onClick = { }
    )
}
