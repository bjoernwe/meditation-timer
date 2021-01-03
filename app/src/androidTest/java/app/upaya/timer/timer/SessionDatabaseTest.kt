package app.upaya.timer.timer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.upaya.timer.sessions.Session
import app.upaya.timer.sessions.SessionDao
import app.upaya.timer.sessions.SessionDatabase
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

    @Before
    fun createDb() {

        // Initialize Room DB (in memory)
        db = initSessionDatabase()
        sessionDao = db.sessionDao

        // Add two sessions
        sessionDao.insert(Session(length = 1))
        sessionDao.insert(Session(length = 3))
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

        // GIVEN a DB with two sessions
        // WHEN the number of sessions is requested
        val sessionCount = sessionDao.getSessionCount()

        // THEN it matches the two added sessions
        assert(sessionCount.getOrAwaitValue() == 2)

    }

    @Test
    fun getAvgSessionLength() {

        // GIVEN a DB with two sessions
        // WHEN the session average is requested
        val sessionAvg = sessionDao.getSessionAvg()

        // THEN it matches the session's average
        assert(sessionAvg.getOrAwaitValue() == 2f)

    }

    @Test
    fun getMaxSessionLength() {

        // GIVEN a DB with two sessions
        // WHEN the max session length is requested
        val sessionMax = sessionDao.getSessionMax()

        // THEN it matches the longer of the two sessions
        assert(sessionMax.getOrAwaitValue() == 3)

    }

}
