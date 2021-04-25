package app.upaya.timer.ui.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.ui.tooling.preview.Preview
import app.upaya.timer.ui.composables.entities.MaterialRing


@ExperimentalMaterialApi
@Composable
fun TimerRing(activated: Boolean, onClick: () -> Unit) {

    // Ripple effect when clicking ring
    val rippleIndication = rememberRipple(
            bounded = false,
            color = MaterialTheme.colors.primary
    )

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .clickable(
                onClick = onClick,
                enabled = !activated,
                indication = rippleIndication,
                interactionSource = remember { MutableInteractionSource() },
            )
    ) {

        val maxSize = min(maxWidth, maxHeight)

        // Ring
        MaterialRing(
            size = maxSize,
            thickness = 6.dp,
            depth = 4.dp,
            color = if (activated) Color.White else MaterialTheme.colors.primary
        )

    }

}


@ExperimentalMaterialApi
@Composable
@Preview
fun TimerRingPreview() {
    TimerRing(
        activated = false,
        onClick = { }
    )
}
