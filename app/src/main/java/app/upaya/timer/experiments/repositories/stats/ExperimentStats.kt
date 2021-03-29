package app.upaya.timer.experiments.repositories.stats

import java.util.*


data class ExperimentStats(
        val count: Int = 0,
        val avgLength: Double? = null,
        val totalLength: Int? = null,
        val date: Date? = null
)
