package app.upaya.timer.session.repository.stats

import app.upaya.timer.experiments.repository.stats.ExperimentStats
import app.upaya.timer.session.repository.SessionLog
import java.util.*


fun SessionLog.length() : Int? {
    val start = startDate
    val end = endDate
    if (start == null || end == null) return null
    return ((end.time - start.time) / 1000).toInt()
}


fun List<SessionLog>.filterValid() : List<SessionLog> {
    return this.filter { s -> s.length() != null }
}


fun List<SessionLog>.lenghts() : List<Int> {
    return this.filterValid().map { s -> s.length()!! }
}


fun List<SessionLog>.calcStats() : ExperimentStats {

    val sessionLengths = this.lenghts()

    // Return empty
    if (sessionLengths.isEmpty())
        return ExperimentStats(
            count = 0,
            avgLength = null,
            totalLength = null,
            date = null
        )

    // Stats
    val avgLength = sessionLengths.average()
    val totalLength = sessionLengths.sum()
    val avgInitDate = Date(this.map { s -> s.initDate.time }.average().toLong())

    return ExperimentStats(
            count = sessionLengths.size,
            avgLength = avgLength,
            totalLength = totalLength,
            date = avgInitDate
    )

}
