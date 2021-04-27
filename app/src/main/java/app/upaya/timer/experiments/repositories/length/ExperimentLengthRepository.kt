package app.upaya.timer.experiments.repositories.length

import android.content.Context
import app.upaya.timer.R


class ExperimentLengthRepository(context: Context) : IExperimentLengthRepository {

    private val defaultExperimentLength = 10.0F
    private val prefExperimentLengthId = context.getString(R.string.pref_session_length)
    private val prefs = with (context.applicationContext) {
        getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)
    }

    override fun loadExperimentLength(): Double {
        return prefs.getFloat(prefExperimentLengthId, defaultExperimentLength).toDouble()
    }

    override fun storeExperimentLength(experimentLength: Double) {
        with (prefs.edit()) {
            putFloat(prefExperimentLengthId, experimentLength.toFloat())
            apply()
        }
    }

}
