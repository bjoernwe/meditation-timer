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

    private val numberOfSessionDays = 100
    private val numberOfSessionsPerDay = 2
    private val numberOfSessionsTotal = numberOfSessionsPerDay * numberOfSessionDays
    private val maxSessionLength = numberOfSessionsPerDay
    private val sessionAvg = (1..numberOfSessionsPerDay).toList().average().toFloat()

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
    fun getSessionNumber() {

        // GIVEN a DB with sessions
        // WHEN the number of sessions is requested
        val sessionCount = sessionDao.getCount()

        // THEN it matches the two added sessions
        assert(sessionCount.getOrAwaitValue() == numberOfSessionsTotal)

    }

    @Test
    fun getAvgSessionLength() {

        // GIVEN a DB with sessions
        // WHEN the session average is requested
        val sessionAvg = sessionDao.getAvg()

        // THEN it matches the session's average
        assert(sessionAvg.getOrAwaitValue() == this.sessionAvg)

    }

    @Test
    fun getMaxSessionLength() {

        // GIVEN a DB with sessions
        // WHEN the max session length is requested
        val sessionMax = sessionDao.getMax()

        // THEN it matches the longer of the two sessions
        assert(sessionMax.getOrAwaitValue() == maxSessionLength)

    }

    @Test
    fun getAvgLengthOfLastDays() {

        // GIVEN a DB with sessions
        // WHEN the history of session averages is requested
        val limit = 10
        val avgOfDays = sessionDao.getAvgOfLastDays(limit)

        // THEN there are the right number of days with the right average
        assert(avgOfDays.getOrAwaitValue().size == limit)
        for (result in avgOfDays.getOrAwaitValue()) {
            assert(result.avg_length == sessionAvg)
        }

    }

    @Test
    fun getAvgLengthOfLastWeeks() {

        // GIVEN a DB with sessions
        // WHEN the history of session averages is requested
        val limit = 10
        val avgOfWeeks = sessionDao.getAvgOfLastWeeks(limit)

        // THEN there are the right number of days with the right average
        assert(avgOfWeeks.getOrAwaitValue().size == limit)
        for (result in avgOfWeeks.getOrAwaitValue()) {
            assert(result.avg_length == sessionAvg)
        }

    }

}
