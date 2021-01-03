package app.upaya.timer.sessions

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Session::class], version = 1, exportSchema = false)
abstract class SessionDatabase : RoomDatabase() {

    abstract val sessionDao: SessionDao

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
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}
