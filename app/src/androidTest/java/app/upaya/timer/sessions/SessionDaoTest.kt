package app.upaya.timer.sessions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.MeditationTimerApplication
import app.upaya.timer.getOrAwaitValue
import app.upaya.timer.sessions.room.SessionDao
import app.upaya.timer.sessions.room.SessionDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.text.SimpleDateFormat


@RunWith(AndroidJUnit4::class)
class SessionDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Make coroutines synchronous

    private lateinit var db: SessionDatabase
    private lateinit var sessionDao: SessionDao

    private val sessionLengthsPerDay = listOf(1, 2)
    private val numberOfSessionDays = 100
    private val numberOfSessionsPerDay = sessionLengthsPerDay.size
    private val numberOfSessionsTotal = numberOfSessionsPerDay * numberOfSessionDays
    private val sessionAvg = sessionLengthsPerDay.average().toFloat()
    private val sessionTotal = numberOfSessionDays * sessionLengthsPerDay.sum()

    @Before
    fun createDb() = runBlocking {

        // Initialize Room DB (in memory)
        db = initSessionDatabase()
        sessionDao = db.sessionDao

        // Add sessions (two for the last N days)
        for (i in 1..numberOfSessionDays) {
            val endDate = SimpleDateFormat("yyyy:D").parse("2020:${i}")!!
            for (j in 1..numberOfSessionsPerDay) {
                sessionDao.insert(Session(endDate = endDate, length = j))
            }
        }
    }

    private fun initSessionDatabase(): SessionDatabase {
        return Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext<MeditationTimerApplication>(),
                SessionDatabase::class.java
        )
                .allowMainThreadQueries()
                .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAggregateOfAllSessions() {

        // GIVEN a DB with sessions
        // WHEN an aggregate of all sessions is requested
        val sessionAggregate = sessionDao.getAggregateOfAll()

        // THEN it matches
        assert(sessionAggregate.getOrAwaitValue().sessionCount == numberOfSessionsTotal)
        assert(sessionAggregate.getOrAwaitValue().avgLength == this.sessionAvg)
        assert(sessionAggregate.getOrAwaitValue().totalLength == this.sessionTotal)
    }

    @Test
    fun getAggregateOfLastDays() {

        // GIVEN a DB with sessions
        // WHEN the history of session averages is requested
        val limit = 10
        val avgOfDays = sessionDao.getAggregateOfLastDays(limit)

        // THEN there are the right number of days with the right average
        assert(avgOfDays.getOrAwaitValue().size == limit)
        for (result in avgOfDays.getOrAwaitValue()) {
            assert(result.avgLength == sessionAvg)
            assert(result.sessionCount == numberOfSessionsPerDay)
        }

    }

}
