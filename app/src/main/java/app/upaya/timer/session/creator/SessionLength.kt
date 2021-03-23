package app.upaya.timer.session.creator

import kotlin.math.max


class SessionLength(length: Double) {

    private val minLength: Double = 1.0

    var value = max(length, minLength)
        private set

    fun updateFromRating(rating: Double) : Double {
        if (rating < 0.5) {
            this.decrease()
        } else {
            this.increase()
        }
        return value
    }

    private fun increase() {
        value = value.times(1.1)
    }

    private fun decrease() {
        val newSessionLength = value.times(.8)
        if (newSessionLength >= minLength) {
            value = newSessionLength
        }
    }

}
