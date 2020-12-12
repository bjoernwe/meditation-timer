package app.upaya.timer.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview


@Preview
@Composable
fun MaterialRingPreview() {
    TimerTheme {
        MaterialRing(size = 100.dp, thickness = 10.dp)
    }
}


@Composable
fun MaterialRing(size: Dp, thickness: Dp, depth: Dp = 1.dp, color: Color = MaterialTheme.colors.primary) {

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.background(MaterialTheme.colors.background)) {

        Canvas(Modifier.preferredSize(size).padding(7.dp)) {

            val radius = this.size.minDimension / 2f
            val shadowColorLight = Color(alpha = 24, red = 0, green = 0, blue = 0)
            val keyShadowOffset1 = Offset(depth.div(4f).toPx(), depth.div(2f).toPx())
            val keyShadowOffset2 = Offset(depth.div(2f).toPx(), depth.toPx())

            // ambient shadow
            drawCircle(
                radius = radius,
                color = shadowColorLight,
                style = Stroke(width = thickness.toPx() + depth.times(2).toPx())
            )

            // key shadow 1
            drawCircle(
                radius = radius,
                color = shadowColorLight,
                center = center + keyShadowOffset1,
                style = Stroke(width = thickness.toPx() + depth.div(1).toPx())
            )

            // key shadow 2
            drawCircle(
                    radius = radius,
                    color = shadowColorLight,
                    center = center + keyShadowOffset2,
                    style = Stroke(width = thickness.toPx() + depth.div(1).toPx())
            )

            // circle
            drawCircle(
                radius = radius,
                color = color,
                style = Stroke(width = thickness.toPx())
            )

        }

    }

}
