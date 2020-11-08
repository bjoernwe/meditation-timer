package app.upaya.timer.timer

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.meditation_timer.R
import java.lang.IllegalArgumentException

/* This factory allows passing arguments to the TimerViewModel*/
class TimerViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val initialSessionLength = prefs.getFloat(context.getString(R.string.pref_session_length), 10.0F).toDouble()
            return TimerViewModel(initialSessionLength) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
