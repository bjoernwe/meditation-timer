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
    fun getSessionAvg(): LiveData<Float>

    @Query("SELECT MAX(length) FROM sessions")
    fun getSessionMax(): LiveData<Int>

}
