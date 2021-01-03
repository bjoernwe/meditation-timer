package app.upaya.timer.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val purple200 = Color(0xFFBB86FC)
val purple500 = Color(0xFF6200EE)
val purple700 = Color(0xFF3700B3)
val teal200 = Color(0xFF80CBC4)
val teal500 = Color(0xFF009688)
val teal700 = Color(0xFF00796B)


private val darkColorPalette = darkColors(
        primary = teal200,
        primaryVariant = teal700,
        secondary = purple200,
        background = Color.DarkGray,
        onBackground = Color.White
)


@Composable
fun TimerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = darkColorPalette,
        content = content
    )
}
