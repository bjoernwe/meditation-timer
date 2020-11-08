@file:JvmName("Converter")

package app.upaya.timer.utils

import kotlin.math.round


fun fromSecsToTimeString(seconds: Float) : String {
    val secs = round(seconds).toInt()
    return "%d:%02d:%02d".format(secs/3600, secs/60, secs%60)
}
