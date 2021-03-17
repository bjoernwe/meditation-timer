package app.upaya.timer.session.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.MeditationTimerApplication
import app.upaya.timer.hints.HintRepository
import app.upaya.timer.session.creator.SessionCreator
import app.upaya.timer.session.repository.SessionRepository
import app.upaya.timer.session.repository.room.SessionLogDatabase
import app.upaya.timer.settings.SessionLengthRepository
import java.lang.IllegalArgumentException


/**
 * The factory allows us to pass arguments to the SessionViewModel.
 * It is also used as a "dirty main component" that assembles and injects all the dependencies for
 * the business layer.
 * */
class SessionViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {

            /**
             * Inject Dependencies
             */

            // SessionLogRepository
            val sessionLogDatabase = SessionLogDatabase.getInstance(context)
            val sessionRepository = SessionRepository(
                sessionLogDao = sessionLogDatabase.sessionLogDao,
                externalScope = (context.applicationContext as MeditationTimerApplication).applicationScope
            )

            // HintRepository
            val hintRepository = HintRepository(context)

            // SessionRepository
            val sessionLengthRepository = SessionLengthRepository(context)

            // SessionCreator
            val sessionCreator = SessionCreator(
                hintRepository = hintRepository,
                sessionLengthRepository = sessionLengthRepository,
            )

            @Suppress("UNCHECKED_CAST")
            return SessionViewModel(
                sessionCreator = sessionCreator,
                sessionRepository = sessionRepository,
            ) as T

        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
