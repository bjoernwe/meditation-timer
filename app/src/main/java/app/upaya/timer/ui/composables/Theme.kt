package app.upaya.timer.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import app.upaya.timer.ui.purple200
import app.upaya.timer.ui.teal200
import app.upaya.timer.ui.teal700


private val darkColorPalette = darkColors(
        primary = teal200,
        primaryVariant = teal700,
        secondary = purple200,
        background = Color.DarkGray,
        onBackground = Color.Gray
)

@Composable
fun TimerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = darkColorPalette,
        content = content
    )
}
