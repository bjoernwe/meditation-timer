package app.upaya.timer.timer

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.R
import java.lang.IllegalArgumentException

/* This factory allows passing arguments to the TimerViewModel*/
class TimerViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            val prefs = application.getSharedPreferences(application.getString(R.string.preference_file), Context.MODE_PRIVATE)
            val initialSessionLength = prefs.getFloat(application.getString(R.string.pref_session_length), 10.0F).toDouble()
            @Suppress("UNCHECKED_CAST")
            return TimerViewModel(initialSessionLength) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
