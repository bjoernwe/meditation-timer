package app.upaya.timer.session

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.session_history.SessionHistoryRepository
import app.upaya.timer.session_history.room_entries.SessionEntryDatabase
import java.lang.IllegalArgumentException


/* This factory allows passing arguments to the TimerViewModel*/
class SessionViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {

            val sessionRepository = SessionRepository(context)
            val sessionHistoryRepository = SessionHistoryRepository(SessionEntryDatabase.getInstance(context))

            @Suppress("UNCHECKED_CAST")
            return SessionViewModel(
                sessionRepository = sessionRepository,
                sessionHistoryRepository = sessionHistoryRepository
            ) as T

        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
