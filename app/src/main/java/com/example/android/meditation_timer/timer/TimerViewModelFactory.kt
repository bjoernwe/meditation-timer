package com.example.android.meditation_timer.timer

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.meditation_timer.R
import java.lang.IllegalArgumentException


class TimerViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(application)
            val initialSessionLength = prefs.getFloat(application.getString(R.string.pref_session_length), 10.0F).toDouble()
            return TimerViewModel(initialSessionLength) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
