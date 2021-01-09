package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(session: Session)

    //@Update
    //suspend fun update(session: Session)

    //@Query("SELECT * FROM sessions")
    //suspend fun getAllSessions(): List<Session>

    @Query("SELECT COUNT(*) FROM sessions")
    fun getSessionCount(): LiveData<Int>

    @Query("SELECT AVG(length) FROM sessions")
    fun getSessionAvg(): LiveData<Float?>

    @Query("SELECT MAX(length) FROM sessions")
    fun getSessionMax(): LiveData<Int>

    @Query("""SELECT AVG(length) AS avg_length, strftime('%Y-%m-%d', end_time, 'unixepoch') AS date 
                    FROM sessions GROUP BY strftime('%Y-%m-%d', end_time, 'unixepoch') 
                    ORDER BY end_time DESC LIMIT :limit""")
    fun getAvgLengthOfLastDays(limit: Int = 10): LiveData<List<SessionAvgResult>>

    @Query("""SELECT AVG(length) AS avg_length, strftime('%Y:%W', end_time, 'unixepoch') AS date 
                    FROM sessions GROUP BY strftime('%Y:%W', end_time, 'unixepoch') 
                    ORDER BY end_time DESC LIMIT :limit""")
    fun getAvgLengthOfLastWeeks(limit: Int = 10): LiveData<List<SessionAvgResult>>

}
