package app.upaya.timer.experiments.repositories.logs.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class ExperimentLogDatabaseTest {

    private lateinit var experimentLogDao: ExperimentLogDao
    private lateinit var db: ExperimentLogDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ExperimentLogDatabase::class.java).build()
        experimentLogDao = db.experimentLogDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadExperiment() = runBlocking {

        // GIVEN an ExperimentLogDatabase
        // WHEN an experiment is stored
        val experiment = ExperimentLog(initDate = Date(1000), probeId = UUID.randomUUID())
        experimentLogDao.insert(experiment)

        // THEN it can be retrieved again from the database
        var experiments: List<ExperimentLog> = experimentLogDao.getExperiments().first()
        assertThat(experiments.count(), equalTo(1))
        assertThat(experiments[0].experimentId, equalTo(experiment.experimentId))

        // AND WHEN a second experiment is stored
        val experiment2 = ExperimentLog(initDate = Date(2000), probeId = UUID.randomUUID())
        experimentLogDao.insert(experiment2)

        // THEN it can also be retrieved from the database
        experiments = experimentLogDao.getExperiments().first()
        assertThat(experiments.count(), equalTo(2))
        assertThat(experiments[0].experimentId, equalTo(experiment2.experimentId))
    }
}
