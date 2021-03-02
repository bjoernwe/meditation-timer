package app.upaya.timer.session.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.upaya.timer.session.SessionDetails
import app.upaya.timer.session_history.room.SessionHistoryDao


@Database(entities = [SessionDetails::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class SessionEntryDatabase : RoomDatabase() {

    abstract val sessionEntryDao: SessionEntryDao
    abstract val sessionHistoryDao: SessionHistoryDao

    // Singleton
    companion object {

        @Volatile
        private var INSTANCE: SessionEntryDatabase? = null

        fun getInstance(context: Context): SessionEntryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SessionEntryDatabase::class.java,
                        "session_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
