package app.upaya.timer.session.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.session.room.SessionLogDatabase
import java.lang.IllegalArgumentException


/* This factory allows passing arguments to the SessionViewModel*/
class SessionHistoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SessionHistoryViewModel::class.java)) {

            val sessionRepository = SessionHistoryRepository(SessionLogDatabase.getInstance(context.applicationContext))

            @Suppress("UNCHECKED_CAST")
            return SessionHistoryViewModel(sessionRepository) as T

        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
