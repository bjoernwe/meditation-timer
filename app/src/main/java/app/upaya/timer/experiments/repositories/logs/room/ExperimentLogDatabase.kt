package app.upaya.timer.experiments.repositories.logs.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.upaya.timer.experiments.repositories.logs.ExperimentLog


@Database(entities = [ExperimentLog::class], version = 5, exportSchema = true)
@TypeConverters(Converters::class)
abstract class ExperimentLogDatabase : RoomDatabase() {

    abstract val experimentLogDao: ExperimentLogDao

    // Singleton
    companion object {

        @Volatile
        private var INSTANCE: ExperimentLogDatabase? = null

        fun getInstance(context: Context): ExperimentLogDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExperimentLogDatabase::class.java,
                        "session_database"
                    )
                        //.fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
