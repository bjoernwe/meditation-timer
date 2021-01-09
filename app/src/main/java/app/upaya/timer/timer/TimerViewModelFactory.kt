package app.upaya.timer.timer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


/* This factory allows passing arguments to the TimerViewModel*/
class TimerViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerViewModel(context) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
