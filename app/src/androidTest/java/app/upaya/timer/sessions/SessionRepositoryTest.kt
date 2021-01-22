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
import java.util.*


@RunWith(AndroidJUnit4::class)
class SessionRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: SessionDatabase
    private lateinit var sessionDao: SessionDao
    private lateinit var sessionRepository: ISessionRepository

    @Before
    fun createDb() {
        db = initSessionDatabase()
        sessionDao = db.sessionDao
        sessionRepository = SessionRepository(db)
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
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN an empty SessionRepository
        assert(sessionRepository.sessionCount.getOrAwaitValue() == 0)
        assert(sessionRepository.sessionAvg.getOrAwaitValue() == 0f)

        // WHEN a session is added
        sessionRepository.storeSession(Session(length = 2, endTime = Date(1000L)))

        // THEN the corresponding LiveData is updated accordingly
        assert(sessionRepository.sessionCount.getOrAwaitValue() == 1)
        assert(sessionRepository.sessionAvg.getOrAwaitValue() == 2f)

        // AND WHEN another session is added
        sessionRepository.storeSession(Session(length = 4, endTime = Date(2000L)))

        // THEN the corresponding LiveData is updated accordingly
        assert(sessionRepository.sessionCount.getOrAwaitValue() == 2)
        assert(sessionRepository.sessionAvg.getOrAwaitValue() == 3f)

        // AND the sessions are ordered descendingly for time
        val session0 = sessionRepository.sessions.getOrAwaitValue()[0]
        val session1 = sessionRepository.sessions.getOrAwaitValue()[1]
        assert(session0.endTime > session1.endTime)
    }

}
