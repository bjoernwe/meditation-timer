package app.upaya.timer.sessions

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.timer.TimerRepository
import java.lang.IllegalArgumentException


/* This factory allows passing arguments to the SessionViewModel*/
class SessionViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {

            val sessionRepository = SessionRepository(SessionDatabase.getInstance(context))

            @Suppress("UNCHECKED_CAST")
            return SessionViewModel(sessionRepository) as T

        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
