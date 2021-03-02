package app.upaya.timer.session_history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.MeditationTimerApplication
import app.upaya.timer.getOrAwaitValue
import app.upaya.timer.session_history.room_entries.SessionEntryDao
import app.upaya.timer.session.room.SessionEntryDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class SessionHistoryRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: SessionEntryDatabase
    private lateinit var sessionEntryDao: SessionEntryDao
    private lateinit var sessionHistoryRepository: ISessionHistoryRepository

    @Before
    fun createDb() {
        db = initSessionDatabase()
        sessionEntryDao = db.sessionEntryDao
        sessionHistoryRepository = SessionHistoryRepository(db)
    }

    private fun initSessionDatabase(): SessionEntryDatabase {
        return Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext<MeditationTimerApplication>(),
                SessionEntryDatabase::class.java
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
        var sessionAggregate = sessionHistoryRepository.sessionAggregateOfAll.getOrAwaitValue()
        assert(sessionAggregate.avgLength == 0f)
        assert(sessionAggregate.sessionCount == 0)
        assert(sessionAggregate.totalLength == 0)

        // WHEN a session is added
        sessionHistoryRepository.storeSession(length = 2.0, endDate = Date(1000L))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionHistoryRepository.sessionAggregateOfAll.getOrAwaitValue()
        assert(sessionAggregate.avgLength == 2f)
        assert(sessionAggregate.sessionCount == 1)
        assert(sessionAggregate.totalLength == 2)

        // AND WHEN another session is added
        sessionHistoryRepository.storeSession(length = 4.0, endDate = Date(2000L))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionHistoryRepository.sessionAggregateOfAll.getOrAwaitValue()
        assert(sessionAggregate.avgLength == 3f)
        assert(sessionAggregate.sessionCount == 2)
        assert(sessionAggregate.totalLength == 6)

        // AND the sessions are ordered descendingly for time
        val session0 = sessionHistoryRepository.sessions.getOrAwaitValue()[0]
        val session1 = sessionHistoryRepository.sessions.getOrAwaitValue()[1]
        assert(session0.endDate > session1.endDate)
    }

}
