package app.upaya.timer.timer

import android.content.Context
import app.upaya.timer.R


class TimerRepository(context: Context) : ITimerRepository {

    private val prefSessionLengthId = context.getString(R.string.pref_session_length)
    private val prefs = with (context.applicationContext) {
        getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)
    }

    override fun loadSessionLength(): Double {
        return prefs.getFloat(prefSessionLengthId, 10.0F).toDouble()
    }

    override fun storeSessionLength(sessionLength: Double) {
        with (prefs.edit()) {
            putFloat(prefSessionLengthId, sessionLength.toFloat())
            apply()
        }
    }

}
