package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SessionRepositoryFake : ISessionRepository {

    private val _sessions = MutableLiveData<MutableList<Session>>(ArrayList())

    override val sessionCount: LiveData<Int> = Transformations.map(_sessions) { it.size }
    override val sessionTotal: LiveData<Int> = Transformations.map(_sessions) { it.fold(0) { acc, s -> acc + s.length } }
    override val sessions: LiveData<List<Session>> = Transformations.map(_sessions) { it.takeLast(14) }

    override val sessionAvg = Transformations.map(_sessions) {
        val avg = it.map { s -> s.length }.average().toFloat()
        if (avg.isNaN()) return@map 0f else return@map avg
    }

    override val sessionAggregateOfLastDays: LiveData<List<SessionAggregate>> = Transformations.map(_sessions) { sessions ->
        val dateSessionMap = sessions.groupBy { SimpleDateFormat("y-M-d").format(it.endDate) }
        val sessionAggregates = dateSessionMap.map {
            SessionAggregate(
                    session_count = it.value.size,
                    avg_length = it.value.map { session -> session.length }.average().toFloat(),
                    date = it.value[0].endDate
            )
        }.sortedByDescending { aggregate -> aggregate.date }
        sessionAggregates
    }

    override suspend fun storeSession(length: Double, endDate: Date) {
        _sessions.value!!.add(0, Session(length = length.toInt(), endDate = endDate))
        _sessions.value = _sessions.value  // notify LiveData about change
    }

}
