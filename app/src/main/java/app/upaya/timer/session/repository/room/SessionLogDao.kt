package app.upaya.timer.session.repository.room

import androidx.room.*
import app.upaya.timer.session.repository.SessionLog


@Dao
interface SessionLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sessionLog: SessionLog)

    @Query("SELECT * FROM SESSIONS ORDER BY end_time DESC")
    suspend fun getSessions(): List<SessionLog>

    @Query("SELECT * FROM SESSIONS ORDER BY end_time DESC LIMIT 1")
    suspend fun getLastSession(): SessionLog

}
