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
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN an empty SessionRepository
        var sessionAggregate = sessionRepository.sessionAggregateOfAll.getOrAwaitValue()
        assert(sessionAggregate.avg_length == 0f)
        assert(sessionAggregate.session_count == 0)
        assert(sessionAggregate.total_length == 0)

        // WHEN a session is added
        sessionRepository.storeSession(length = 2.0, endDate = Date(1000L))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionRepository.sessionAggregateOfAll.getOrAwaitValue()
        assert(sessionAggregate.avg_length == 2f)
        assert(sessionAggregate.session_count == 1)
        assert(sessionAggregate.total_length == 2)

        // AND WHEN another session is added
        sessionRepository.storeSession(length = 4.0, endDate = Date(2000L))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionRepository.sessionAggregateOfAll.getOrAwaitValue()
        assert(sessionAggregate.avg_length == 3f)
        assert(sessionAggregate.session_count == 2)
        assert(sessionAggregate.total_length == 6)

        // AND the sessions are ordered descendingly for time
        val session0 = sessionRepository.sessions.getOrAwaitValue()[0]
        val session1 = sessionRepository.sessions.getOrAwaitValue()[1]
        assert(session0.endDate > session1.endDate)
    }

}
