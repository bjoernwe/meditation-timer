package app.upaya.timer.session.repository.stats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.MeditationTimerApplication
import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.SessionLog
import app.upaya.timer.session.repository.SessionRepository
import app.upaya.timer.session.repository.room.SessionLogDao
import app.upaya.timer.session.repository.room.SessionLogDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class SessionStatsRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: SessionLogDatabase
    private lateinit var sessionLogDao: SessionLogDao
    private lateinit var sessionRepository: ISessionRepository
    private lateinit var sessionStatsRepository: ISessionStatsRepository

    @Before
    fun createDb() {
        db = initSessionDatabase()
        sessionLogDao = db.sessionLogDao
        sessionRepository = SessionRepository(db)
        sessionStatsRepository = SessionStatsRepository(db)
    }

    private fun initSessionDatabase(): SessionLogDatabase {
        return Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext<MeditationTimerApplication>(),
                SessionLogDatabase::class.java
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
        var sessionAggregate = sessionStatsRepository.getSessionAggregate()
        assert(sessionAggregate.avgLength == 0f)
        assert(sessionAggregate.sessionCount == 0)
        assert(sessionAggregate.totalLength == 0)

        // WHEN a session is added
        sessionRepository.storeSession(SessionLog( length = 2, endDate = Date(1000L)))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionStatsRepository.getSessionAggregate()
        assert(sessionAggregate.avgLength == 2f)
        assert(sessionAggregate.sessionCount == 1)
        assert(sessionAggregate.totalLength == 2)

        // AND WHEN another session is added
        sessionRepository.storeSession(SessionLog(length = 4, endDate = Date(2000L)))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionStatsRepository.getSessionAggregate()
        assert(sessionAggregate.avgLength == 3f)
        assert(sessionAggregate.sessionCount == 2)
        assert(sessionAggregate.totalLength == 6)
    }

}
