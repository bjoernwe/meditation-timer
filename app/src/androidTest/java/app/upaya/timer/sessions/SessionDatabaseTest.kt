package app.upaya.timer.sessions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.upaya.timer.getOrAwaitValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SessionDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Make coroutines synchronous

    private lateinit var db: SessionDatabase
    private lateinit var sessionDao: SessionDao

    private val numberOfSessionDays = 100

    @Before
    fun createDb() {

        // Initialize Room DB (in memory)
        db = initSessionDatabase()
        sessionDao = db.sessionDao

        // Add sessions (two for the last N days)
        for (i in 1..numberOfSessionDays) {
            val endTime: Long = System.currentTimeMillis() / 1000L - i * (60 * 60 * 24)
            sessionDao.insert(Session(endTime = endTime, length = 1))
            sessionDao.insert(Session(endTime = endTime, length = 2))
        }
    }

    private fun initSessionDatabase(): SessionDatabase {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
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
        assert(sessionCount.getOrAwaitValue() == 2 * numberOfSessionDays)

    }

    @Test
    fun getAvgSessionLength() {

        // GIVEN a DB with sessions
        // WHEN the session average is requested
        val sessionAvg = sessionDao.getSessionAvg()

        // THEN it matches the session's average
        assert(sessionAvg.getOrAwaitValue() == 1.5f)

    }

    @Test
    fun getMaxSessionLength() {

        // GIVEN a DB with sessions
        // WHEN the max session length is requested
        val sessionMax = sessionDao.getSessionMax()

        // THEN it matches the longer of the two sessions
        assert(sessionMax.getOrAwaitValue() == 2)

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
            assert(result.avg_length == 1.5f)
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
            assert(result.avg_length == 1.5f)
        }

    }

}
