package app.upaya.timer.experiments.creator

import kotlin.math.max


class ExperimentLength(initialLength: Double) {

    private val minLength: Double = 1.0

    var value = max(initialLength, minLength)
        private set

    fun updateFromFeedback(feedback: Double) : Double {
        if (feedback < 0.5) {
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
        val decreasedExperimentLength = value.times(.8)
        if (decreasedExperimentLength >= minLength) {
            value = decreasedExperimentLength
        }
    }

}
