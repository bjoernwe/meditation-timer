package app.upaya.timer.session.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.upaya.timer.session.SessionLog
import app.upaya.timer.session_history.room.SessionHistoryDao


@Database(entities = [SessionLog::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class SessionLogDatabase : RoomDatabase() {

    abstract val sessionLogDao: SessionLogDao
    abstract val sessionHistoryDao: SessionHistoryDao

    // Singleton
    companion object {

        @Volatile
        private var INSTANCE: SessionLogDatabase? = null

        fun getInstance(context: Context): SessionLogDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SessionLogDatabase::class.java,
                        "session_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
