package app.upaya.timer.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val blueGrey800 = Color(0xff37474f)
val blueGrey800Light = Color(0xff62727b)
val blueGrey800Dark = Color(0xff102027)
val cyan900 = Color(0xff006064)
val cyan900Light = Color(0xff428e92)
val cyan900Dark = Color(0xff00363a)
val lightGreenA100 = Color(0xffccff90)
val lightGreenA100Light = Color(0xffffffc2)
val lightGreenA100Dark = Color(0xff99cc60)
val lightGreenA200 = Color(0xffb2ff59)
val lightGreenA200Light = Color(0xffe7ff8c)
val lightGreenA200Dark = Color(0xff7ecb20)
val deepOrange700 = Color(0xffe64a19)
val deepOrange700Light = Color(0xffff7d47)
val deepOrange700Dark = Color(0xffac0800)
val grey800 = Color(0xff424242)
val grey800Light = Color(0xff6d6d6d)
val grey800Dark = Color(0xff1b1b1b)
val purple200 = Color(0xFFBB86FC)
val purple500 = Color(0xFF6200EE)
val purple700 = Color(0xFF3700B3)
val teal200 = Color(0xFF80CBC4)
val teal500 = Color(0xFF009688)
val teal700 = Color(0xFF00796B)
val tealA700 = Color(0xff00bfa5)
val tealA700Light = Color(0xff5df2d6)
val tealA700Dark = Color(0xff008e76)


private val darkColorPalette = darkColors(
        primary = tealA700,
        surface = blueGrey800,
        background = blueGrey800Dark,
        onSurface = Color.White,
        onBackground = mixColors(cyan900, blueGrey800),
)

@Composable
fun TimerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = darkColorPalette,
        content = content
    )
}


private fun mixColors(color1: Color, color2: Color): Color {
    return Color(
            red = (color1.red + color2.red) / 2f,
            green = (color1.green + color2.green) / 2f,
            blue = (color1.blue + color2.blue) / 2f,
            alpha = (color1.alpha + color2.alpha) / 2f,
    )
}
