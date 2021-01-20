package app.upaya.timer.sessions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.MeditationTimerApplication
import app.upaya.timer.getOrAwaitValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


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
    fun createDb() {

        // Initialize Room DB (in memory)
        db = initSessionDatabase()
        sessionDao = db.sessionDao

        // Add sessions (two for the last N days)
        for (i in 1..numberOfSessionDays) {
            val endTime: Long = System.currentTimeMillis() / 1000L - i * (60 * 60 * 24)
            for (j in 1..numberOfSessionsPerDay) {
                sessionDao.insert(Session(endTime = endTime, length = j))
            }
        }
    }

    private fun initSessionDatabase(): SessionDatabase {
        val context = ApplicationProvider.getApplicationContext<MeditationTimerApplication>()
        return Room.inMemoryDatabaseBuilder(context, SessionDatabase::class.java)
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
        val sessionCount = sessionDao.getSessionCount()

        // THEN it matches the two added sessions
        assert(sessionCount.getOrAwaitValue() == numberOfSessionsTotal)

    }

    @Test
    fun getAvgSessionLength() {

        // GIVEN a DB with sessions
        // WHEN the session average is requested
        val sessionAvg = sessionDao.getSessionAvg()

        // THEN it matches the session's average
        assert(sessionAvg.getOrAwaitValue() == this.sessionAvg)

    }

    @Test
    fun getMaxSessionLength() {

        // GIVEN a DB with sessions
        // WHEN the max session length is requested
        val sessionMax = sessionDao.getSessionMax()

        // THEN it matches the longer of the two sessions
        assert(sessionMax.getOrAwaitValue() == maxSessionLength)

    }

    @Test
    fun getAvgLengthOfLastDays() {

        // GIVEN a DB with sessions
        // WHEN the history of session averages is requested
        val limit = 10
        val avgOfDays = sessionDao.getAvgLengthOfLastDays(limit)

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
        val avgOfWeeks = sessionDao.getAvgLengthOfLastWeeks(limit)

        // THEN there are the right number of days with the right average
        assert(avgOfWeeks.getOrAwaitValue().size == limit)
        for (result in avgOfWeeks.getOrAwaitValue()) {
            assert(result.avg_length == sessionAvg)
        }

    }

}
