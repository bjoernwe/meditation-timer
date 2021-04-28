package app.upaya.timer.ui

import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import java.util.*


fun fromSecsToTimeString(seconds: Int?) : String {
    val secs = seconds ?: 0
    return "%d:%02d:%02d".format(secs/3600, (secs/60)%60, secs%60)
}


fun List<ExperimentLog>.toCSV() : String {
    val lines = this.map {
        listOf(
            it.sessionId,
            it.hint,
            it.initDate.toTimestamp(),
            it.startDate.toTimestamp(),
            it.endDate.toTimestamp(),
            it.ratingDate.toTimestamp(),
            it.rating
        ).joinToString(separator = ",") }
    return lines.joinToString(separator = "\n")
}


private fun Date?.toTimestamp() : Long? {
    return this?.time?.div(1000)
}
