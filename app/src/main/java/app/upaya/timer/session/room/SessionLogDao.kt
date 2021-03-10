package app.upaya.timer.session.room

import androidx.lifecycle.LiveData
import androidx.room.*
import app.upaya.timer.session.SessionLog


@Dao
interface SessionLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sessionLog: SessionLog)

    @Query("SELECT * FROM SESSIONS ORDER BY end_time DESC LIMIT 1")
    fun getLastSession(): LiveData<SessionLog>

}
