package app.upaya.timer.session_history

import androidx.lifecycle.LiveData
import app.upaya.timer.session.room.SessionLogDatabase


class SessionHistoryRepository(sessionLogDatabase: SessionLogDatabase) : ISessionHistoryRepository {

    private val numberOfAggregatedDays = 14
    private val sessionHistoryDao = sessionLogDatabase.sessionHistoryDao

    override val sessionAggregateOfAll: LiveData<SessionAggregate> = sessionHistoryDao.getAggregateOfAll()
    override val sessionAggregateOfLastDays = sessionHistoryDao.getAggregateOfLastDays(numberOfAggregatedDays)

}
