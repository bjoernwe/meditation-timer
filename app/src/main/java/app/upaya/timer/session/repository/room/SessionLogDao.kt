package app.upaya.timer.session.repository.room

import androidx.room.*
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.Flow


@Dao
interface SessionLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sessionLog: SessionLog)

    @Query("SELECT * FROM SESSIONS ORDER BY init_time DESC")
    fun getSessions(): Flow<List<SessionLog>>

    @Query("SELECT * FROM SESSIONS ORDER BY init_time DESC LIMIT 1")
    fun getLastSession(): Flow<SessionLog>

}
