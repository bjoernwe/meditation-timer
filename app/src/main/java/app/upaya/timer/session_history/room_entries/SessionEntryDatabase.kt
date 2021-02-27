package app.upaya.timer.session_history.room_entries

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [SessionEntry::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class SessionEntryDatabase : RoomDatabase() {

    abstract val sessionEntryDao: SessionEntryDao

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
