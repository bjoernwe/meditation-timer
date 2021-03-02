package app.upaya.timer.session_history

import app.upaya.timer.session.room.SessionEntryDatabase
import java.util.*


class SessionHistoryRepository(sessionEntryDatabase: SessionEntryDatabase) : ISessionHistoryRepository {

    private val numberOfAggregatedDays = 14
    private val sessionHistoryDao = sessionEntryDatabase.sessionHistoryDao

    override val sessionAggregateOfAll = sessionHistoryDao.getAggregateOfAll()
    override val sessionAggregateOfLastDays = sessionHistoryDao.getAggregateOfLastDays(numberOfAggregatedDays)

}
