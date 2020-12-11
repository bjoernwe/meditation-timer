package app.upaya.timer.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


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
