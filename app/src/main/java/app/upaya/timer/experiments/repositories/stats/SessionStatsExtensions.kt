package app.upaya.timer.experiments.repositories.stats

import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import java.util.*


fun ExperimentLog.length() : Int? {
    val start = startDate
    val end = endDate
    if (start == null || end == null) return null
    return ((end.time - start.time) / 1000).toInt()
}


fun List<ExperimentLog>.filterValid() : List<ExperimentLog> {
    return this.filter { s -> s.length() != null }
}


fun List<ExperimentLog>.lenghts() : List<Int> {
    return this.filterValid().map { s -> s.length()!! }
}


fun List<ExperimentLog>.calcStats() : ExperimentStats {

    val experimentLengths = this.lenghts()

    // Return empty
    if (experimentLengths.isEmpty())
        return ExperimentStats(
            count = 0,
            avgLength = null,
            totalLength = null,
            date = null
        )

    // Stats
    val avgLength = experimentLengths.average()
    val totalLength = experimentLengths.sum()
    val avgInitDate = Date(this.map { s -> s.initDate.time }.average().toLong())

    return ExperimentStats(
        count = experimentLengths.size,
        avgLength = avgLength,
        totalLength = totalLength,
        date = avgInitDate
    )

}
