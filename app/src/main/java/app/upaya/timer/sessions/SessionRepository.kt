package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.upaya.timer.sessions.room.SessionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class SessionRepository(sessionDatabase: SessionDatabase) : ISessionRepository {

    private val sessionDao = sessionDatabase.sessionDao

    private val numberOfAggregatedDays = 14

    private val _sessionAggregateOfAll = sessionDao.getAggregateOfAll()
    override val sessionAggregateOfAll = Transformations.map(_sessionAggregateOfAll) {
        it.toSessionAggregate()
    }

    private val _sessionAggregateOfLastDays = sessionDao.getAggregateOfLastDays(numberOfAggregatedDays)
    override val sessionAggregateOfLastDays = Transformations.map(_sessionAggregateOfLastDays) {
        it.map { aggregate -> aggregate.toSessionAggregate() }
    }

    override val sessions: LiveData<List<Session>> = sessionDao.getSessions()

    override suspend fun storeSession(length: Double, endDate: Date) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(Session(length = length.toInt(), endDate = endDate))
        }
    }

}
