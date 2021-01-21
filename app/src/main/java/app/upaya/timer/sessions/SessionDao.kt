package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(session: Session)

    //@Update
    //suspend fun update(session: Session)

    @Query("SELECT COUNT(*) FROM sessions")
    fun getCount(): LiveData<Int>

    @Query("SELECT AVG(length) FROM sessions")
    fun getAvg(): LiveData<Float?>

    @Query("SELECT MAX(length) FROM sessions")
    fun getMax(): LiveData<Int>

    @Query("SELECT * FROM SESSIONS ORDER BY end_time DESC LIMIT :limit")
    fun getSessions(limit: Int = 25): LiveData<List<Session>>

    @Query("""SELECT AVG(length) AS avg_length, strftime('%Y-%m-%d', end_time, 'unixepoch') AS date 
                    FROM sessions GROUP BY strftime('%Y-%m-%d', end_time, 'unixepoch') 
                    ORDER BY end_time DESC LIMIT :limit""")
    fun getAvgOfLastDays(limit: Int = 10): LiveData<List<SessionAvgResult>>

    @Query("""SELECT AVG(length) AS avg_length, strftime('%Y:%W', end_time, 'unixepoch') AS date 
                    FROM sessions GROUP BY strftime('%Y:%W', end_time, 'unixepoch') 
                    ORDER BY end_time DESC LIMIT :limit""")
    fun getAvgOfLastWeeks(limit: Int = 10): LiveData<List<SessionAvgResult>>

    @Query("""SELECT SUM(length) AS total_length FROM sessions""")
    fun getTotalLength(): LiveData<Int>

}
