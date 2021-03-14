package app.upaya.timer.session

import kotlin.math.max


class SessionLength(length: Double) {

    private val minLength: Double = 1.0

    var value = max(length, minLength)
        private set

    fun increase() {
        value = value.times(1.1)
    }

    fun decrease() {
        val newSessionLength = value.times(.8)
        if (newSessionLength >= minLength) {
            value = newSessionLength
        }
    }

}
