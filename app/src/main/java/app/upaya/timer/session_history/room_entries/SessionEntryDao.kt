package app.upaya.timer.session_history.room_entries

import androidx.lifecycle.LiveData
import androidx.room.*
import app.upaya.timer.session_history.SessionAggregate


@Dao
interface SessionEntryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(sessionEntry: SessionEntry)

    @Query("SELECT * FROM SESSIONS ORDER BY end_time DESC LIMIT :limit")
    fun getSessions(limit: Int = 25): LiveData<List<SessionEntry>>

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(length) AS avgLength, 
                    TOTAL(length) AS totalLength, AVG(end_time) AS date FROM sessions""")
    fun getAggregateOfAll(): LiveData<SessionAggregate>

    @Query("""SELECT COUNT(*) AS sessionCount, AVG(length) AS avgLength, 
                    TOTAL(length) AS totalLength, AVG(end_time) AS date FROM sessions 
                    GROUP BY strftime('%Y-%m-%d', end_time, 'unixepoch') 
                    ORDER BY end_time DESC LIMIT :limit""")
    fun getAggregateOfLastDays(limit: Int = 10): LiveData<List<SessionAggregate>>

}
