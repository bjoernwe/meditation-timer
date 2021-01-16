package app.upaya.timer.sessions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.upaya.timer.getOrAwaitValue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SessionMigrationTest {

    private val TEST_DB = "migration-test"
    private val ALL_MIGRATIONS = arrayOf(MIGRATION_1_2)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Make coroutines synchronous

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            SessionDatabase::class.java.canonicalName,
            FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun testAllMigrations() {
        // Create earliest version of the database.
        helper.createDatabase(TEST_DB, 1).apply {
            execSQL("INSERT INTO sessions VALUES (1, 123.45, 42)")
            close()
        }

        // Open latest version of the database. Room will validate the schema
        // once all migrations execute.
        val db = Room.databaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                SessionDatabase::class.java,
                TEST_DB
        ).addMigrations(*ALL_MIGRATIONS).build().apply {
            openHelper.writableDatabase
            close()
        }

        val sessions = db.sessionDao.getSessions().getOrAwaitValue()
        assert(sessions[0].sessionId == 1L)
        assert(sessions[0].endTime == 123L)
        assert(sessions[0].length == 42)
    }

}
