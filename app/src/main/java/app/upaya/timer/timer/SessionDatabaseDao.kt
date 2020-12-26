package app.upaya.timer.timer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SessionDatabaseDao {

    @Insert
    fun insert(session: Session)

    @Query("SELECT * FROM sessions")
    fun getAll(): LiveData<List<Session>>

}
