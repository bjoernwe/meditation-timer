package app.upaya.timer.session.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.upaya.timer.session.SessionLog
import app.upaya.timer.session.SessionLogRepositoryFake
import java.text.SimpleDateFormat


class SessionHistoryRepositoryFake(sessionLogRepositoryFake: SessionLogRepositoryFake) : ISessionHistoryRepository {

    private val sessions: LiveData<List<SessionLog>> = sessionLogRepositoryFake.sessions

    override val sessionAggregateOfAll: LiveData<SessionAggregate> = Transformations.map(sessions) { sessions ->
        sessions.aggregate()
    }

    override val sessionAggregateOfLastDays: LiveData<List<SessionAggregate>> = Transformations.map(sessions) { sessions ->
        sessions.groupBy { SimpleDateFormat("y-M-d").format(it.endDate) }
                .map { it.value.aggregate() }
                .sortedByDescending { aggregate -> aggregate.date }
    }

}
