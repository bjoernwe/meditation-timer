package app.upaya.timer.sessions

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Session::class], version = 2, exportSchema = true)
abstract class SessionDatabase : RoomDatabase() {

    abstract val sessionDao: SessionDao

    // Singleton
    companion object {

        @Volatile
        private var INSTANCE: SessionDatabase? = null

        fun getInstance(context: Context): SessionDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SessionDatabase::class.java,
                            "session_database"
                    ).addMigrations(MIGRATION_1_2).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""CREATE TABLE sessions_new (sessionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                                end_time INTEGER NOT NULL, length INTEGER NOT NULL)""")
        database.execSQL("""INSERT INTO sessions_new (sessionId, end_time, length)
                                SELECT sessionId, CAST(end_time AS INTEGER), length FROM sessions""")
        database.execSQL("DROP TABLE sessions")
        database.execSQL("ALTER TABLE sessions_new RENAME TO sessions")
        database.execSQL("CREATE INDEX IF NOT EXISTS `index_sessions_end_time` ON `sessions` (`end_time`)")
    }
}
