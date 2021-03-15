package app.upaya.timer.session.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.upaya.timer.session.repository.SessionLog


@Database(entities = [SessionLog::class], version = 4, exportSchema = true)
@TypeConverters(Converters::class)
abstract class SessionLogDatabase : RoomDatabase() {

    abstract val sessionLogDao: SessionLogDao
    abstract val sessionStatsDao: SessionStatsDao

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
                    )
                        .fallbackToDestructiveMigrationFrom(3)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
