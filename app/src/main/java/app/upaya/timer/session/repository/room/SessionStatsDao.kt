package app.upaya.timer.session.repository.room

import androidx.room.*
import app.upaya.timer.session.repository.stats.SessionAggregate
import kotlinx.coroutines.flow.Flow


@Dao
interface SessionStatsDao {

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(end_time - start_time) AS avgLength, 
                    TOTAL(end_time - start_time) AS totalLength, AVG(end_time) AS date FROM sessions
                    WHERE end_time IS NOT NULL""")
    fun getAggregate(): Flow<SessionAggregate>

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(end_time - start_time) AS avgLength, 
                    TOTAL(end_time - start_time) AS totalLength, AVG(end_time) AS date FROM sessions 
                    WHERE end_time IS NOT NULL
                    GROUP BY strftime('%Y-%m-%d', end_time, 'unixepoch') 
                    ORDER BY end_time DESC LIMIT :limit""")
    fun getAggregatesOfLastDays(limit: Int = 10): Flow<List<SessionAggregate>>

}
