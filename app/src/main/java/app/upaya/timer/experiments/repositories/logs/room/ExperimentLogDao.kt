package app.upaya.timer.experiments.repositories.logs.room

import androidx.room.*
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.Flow


@Dao
interface ExperimentLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(experimentLog: ExperimentLog)

    @Query("SELECT * FROM EXPERIMENTS ORDER BY init_time DESC")
    fun getExperiments(): Flow<List<ExperimentLog>>

    @Query("SELECT * FROM EXPERIMENTS ORDER BY init_time DESC LIMIT 1")
    fun getLastExperiment(): Flow<ExperimentLog>

}
