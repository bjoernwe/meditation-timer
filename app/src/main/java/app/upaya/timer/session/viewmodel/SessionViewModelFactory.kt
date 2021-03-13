package app.upaya.timer.session.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.session.SessionHandler
import app.upaya.timer.session.repository.SessionRepository
import app.upaya.timer.session.repository.stats.SessionStatsRepository
import app.upaya.timer.session.repository.room.SessionLogDatabase
import app.upaya.timer.settings.SessionLengthRepository
import java.lang.IllegalArgumentException


/* This factory allows passing arguments to the TimerViewModel*/
class SessionViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {

            val sessionLengthRepository = SessionLengthRepository(context)
            val initialSessionLength = sessionLengthRepository.loadSessionLength()
            val sessionLogDatabase = SessionLogDatabase.getInstance(context)
            val sessionLogRepository = SessionRepository(sessionLogDatabase.sessionLogDao)
            val sessionStatsRepository = SessionStatsRepository(sessionLogDatabase.sessionStatsDao)
            val sessionHandler = SessionHandler(
                sessionRepository = sessionLogRepository,
                initialSessionLength = initialSessionLength,
            )

            @Suppress("UNCHECKED_CAST")
            return SessionViewModel(
                sessionHandler = sessionHandler,
                sessionStatsRepository = sessionStatsRepository
            ) as T

        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
