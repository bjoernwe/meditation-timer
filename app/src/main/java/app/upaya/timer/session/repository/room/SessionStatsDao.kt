package app.upaya.timer.session.repository.room

import androidx.room.*
import app.upaya.timer.session.repository.SessionLog
import app.upaya.timer.session.repository.stats.SessionAggregate


@Dao
interface SessionStatsDao {

    @Query("SELECT * FROM SESSIONS ORDER BY end_time DESC LIMIT :limit")
    suspend fun getSessions(limit: Int = 25): List<SessionLog>

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(length) AS avgLength, 
                    TOTAL(length) AS totalLength, AVG(end_time) AS date FROM sessions""")
    suspend fun getAggregateOfAll(): SessionAggregate

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(length) AS avgLength, 
                    TOTAL(length) AS totalLength, AVG(end_time) AS date FROM sessions 
                    GROUP BY strftime('%Y-%m-%d', end_time, 'unixepoch') 
                    ORDER BY end_time DESC LIMIT :limit""")
    suspend fun getAggregateOfLastDays(limit: Int = 10): List<SessionAggregate>

}