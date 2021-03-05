package app.upaya.timer.session_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import app.upaya.timer.session.SessionLog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SessionHistoryRepositoryFake : ISessionHistoryRepository {

    private val _sessions = MutableLiveData<MutableList<SessionLog>>(ArrayList())

    override val sessions: LiveData<List<SessionLog>> = Transformations.map(_sessions) { it.takeLast(14) }

    override val sessionAggregateOfAll: LiveData<SessionAggregate> = Transformations.map(_sessions) { sessions ->
        sessions.aggregate()
    }

    override val sessionAggregateOfLastDays: LiveData<List<SessionAggregate>> = Transformations.map(_sessions) { sessions ->
        sessions.groupBy { SimpleDateFormat("y-M-d").format(it.endDate) }
                .map { it.value.aggregate() }
                .sortedByDescending { aggregate -> aggregate.date }
    }

    override suspend fun storeSession(length: Double, endDate: Date) {
        _sessions.value!!.add(0, SessionLog(length = length.toInt(), endDate = endDate))
        _sessions.value = _sessions.value  // notify LiveData about change
    }

}
