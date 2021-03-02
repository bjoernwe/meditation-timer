package app.upaya.timer.session.room

import androidx.lifecycle.LiveData
import androidx.room.*
import app.upaya.timer.session.SessionDetails


@Dao
interface SessionEntryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(sessionDetails: SessionDetails)

    @Query("SELECT * FROM SESSIONS ORDER BY end_time DESC LIMIT :limit")
    fun getSessions(limit: Int = 25): LiveData<List<SessionDetails>>

}
