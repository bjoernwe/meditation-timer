package app.upaya.timer.session.repository.room

import androidx.room.*
import app.upaya.timer.session.repository.stats.SessionAggregate
import kotlinx.coroutines.flow.Flow


@Dao
interface SessionStatsDao {

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(length) AS avgLength, 
                    TOTAL(length) AS totalLength, AVG(end_time) AS date FROM sessions""")
    fun getAggregate(): Flow<SessionAggregate>

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(length) AS avgLength, 
                    TOTAL(length) AS totalLength, AVG(end_time) AS date FROM sessions 
                    GROUP BY strftime('%Y-%m-%d', end_time, 'unixepoch') 
                    ORDER BY end_time DESC LIMIT :limit""")
    fun getAggregatesOfLastDays(limit: Int = 10): Flow<List<SessionAggregate>>

}
