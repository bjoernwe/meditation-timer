package app.upaya.timer.sessions.room

import androidx.lifecycle.LiveData
import androidx.room.*
import app.upaya.timer.sessions.Session
import app.upaya.timer.sessions.SessionAggregate


@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(session: Session)

    @Query("SELECT * FROM SESSIONS ORDER BY end_time DESC LIMIT :limit")
    fun getSessions(limit: Int = 25): LiveData<List<Session>>

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(length) AS avgLength, 
                    TOTAL(length) AS totalLength, AVG(end_time) AS date FROM sessions""")
    fun getAggregateOfAll(): LiveData<SessionAggregate>

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(length) AS avgLength, 
                    TOTAL(length) AS totalLength, AVG(end_time) AS date FROM sessions 
                    GROUP BY strftime('%Y-%m-%d', end_time, 'unixepoch') 
                    ORDER BY end_time DESC LIMIT :limit""")
    fun getAggregateOfLastDays(limit: Int = 10): LiveData<List<SessionAggregate>>

}
