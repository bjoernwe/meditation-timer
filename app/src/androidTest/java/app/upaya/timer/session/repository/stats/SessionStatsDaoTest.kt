package app.upaya.timer.session.repository.stats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.MeditationTimerApplication
import app.upaya.timer.session.repository.room.SessionLogDatabase
import app.upaya.timer.session.repository.SessionLog
import app.upaya.timer.session.repository.room.SessionStatsDao
import app.upaya.timer.session.repository.room.SessionLogDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.text.SimpleDateFormat


@RunWith(AndroidJUnit4::class)
class SessionStatsDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Make coroutines synchronous

    private lateinit var db: SessionLogDatabase
    private lateinit var sessionLogDao: SessionLogDao
    private lateinit var sessionStatsDao: SessionStatsDao

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
        sessionLogDao = db.sessionLogDao
        sessionStatsDao = db.sessionStatsDao

        // Add sessions (two for the last N days)
        for (i in 1..numberOfSessionDays) {
            val endDate = SimpleDateFormat("yyyy:D").parse("2020:${i}")!!
            for (j in 1..numberOfSessionsPerDay) {
                sessionLogDao.insert(SessionLog(endDate = endDate, length = j))
            }
        }
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
    suspend fun getAggregateOfAllSessions() {

        // GIVEN a DB with sessions
        // WHEN an aggregate of all sessions is requested
        val sessionAggregate = sessionStatsDao.getAggregateOfAll()

        // THEN it matches
        assert(sessionAggregate.sessionCount == numberOfSessionsTotal)
        assert(sessionAggregate.avgLength == this.sessionAvg)
        assert(sessionAggregate.totalLength == this.sessionTotal)
    }

    @Test
    suspend fun getAggregateOfLastDays() {

        // GIVEN a DB with sessions
        // WHEN the history of session averages is requested
        val limit = 10
        val avgOfDays = sessionStatsDao.getAggregateOfLastDays(limit)

        // THEN there are the right number of days with the right average
        assert(avgOfDays.size == limit)
        for (result in avgOfDays) {
            assert(result.avgLength == sessionAvg)
            assert(result.sessionCount == numberOfSessionsPerDay)
        }

    }

}
