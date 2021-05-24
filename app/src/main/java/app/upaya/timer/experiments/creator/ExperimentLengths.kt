package app.upaya.timer.experiments.creator

import java.util.*


class ExperimentLengths(private val defaultFactory: (UUID) -> ExperimentLength) {

    private val experimentLengths: MutableMap<UUID, ExperimentLength> = mutableMapOf()

    operator fun get(key: UUID): ExperimentLength {
        experimentLengths.computeIfAbsent(key, defaultFactory)
        return experimentLengths[key] ?: defaultFactory(key)
    }

    operator fun set(key: UUID, value: ExperimentLength) {
        experimentLengths[key] = value
    }

    fun updateFromFeedback(hint: UUID, feedback: Double): Double {
        return this[hint].updateFromFeedback(feedback)
    }

}
