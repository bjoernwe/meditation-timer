package app.upaya.timer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SessionDao {

    @Insert
    suspend fun insert(session: Session)

    @Query("SELECT COUNT(*) FROM sessions")
    fun getSessionsNumber(): LiveData<Int>

    @Query("SELECT AVG(length) FROM sessions")
    fun getSessionsAvg(): LiveData<Float>

    @Query("SELECT MAX(length) FROM sessions")
    fun getSessionsMax(): LiveData<Int>

}
