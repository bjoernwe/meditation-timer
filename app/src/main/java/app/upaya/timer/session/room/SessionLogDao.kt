package app.upaya.timer.session.room

import androidx.lifecycle.LiveData
import androidx.room.*
import app.upaya.timer.session.SessionLog


@Dao
interface SessionLogDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(sessionLog: SessionLog)

    @Query("SELECT * FROM SESSIONS ORDER BY end_time DESC LIMIT :limit")
    fun getSessions(limit: Int = 25): LiveData<List<SessionLog>>

}