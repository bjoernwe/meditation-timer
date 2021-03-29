package app.upaya.timer.session.repository.room

import androidx.room.*
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.Flow


@Dao
interface SessionLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(experimentLog: ExperimentLog)

    @Query("SELECT * FROM SESSIONS ORDER BY init_time DESC")
    fun getSessions(): Flow<List<ExperimentLog>>

    @Query("SELECT * FROM SESSIONS ORDER BY init_time DESC LIMIT 1")
    fun getLastSession(): Flow<ExperimentLog>

}
