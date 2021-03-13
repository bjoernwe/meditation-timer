package app.upaya.timer.session

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.session.room.SessionLogDatabase
import app.upaya.timer.settings.SessionLengthRepository
import java.lang.IllegalArgumentException


/* This factory allows passing arguments to the TimerViewModel*/
class SessionViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {

            val sessionLengthRepository = SessionLengthRepository(context)
            val initialSessionLength = sessionLengthRepository.loadSessionLength()

            val sessionLogDatabase = SessionLogDatabase.getInstance(context)
            val sessionLogRepository = SessionLogRepository(sessionLogDatabase)
            val sessionHandler = SessionHandler(
                sessionLogRepository = sessionLogRepository,
                initialSessionLength = initialSessionLength,
            )

            @Suppress("UNCHECKED_CAST")
            return SessionViewModel(sessionHandler = sessionHandler) as T

        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
