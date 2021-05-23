package app.upaya.timer.experiments.repositories.length

import android.content.Context
import app.upaya.timer.R


class ExperimentLengthRepository(context: Context) : IExperimentLengthRepository {

    private val prefs = with (context.applicationContext) {
        getSharedPreferences(getString(R.string.experiment_length_pref_file), Context.MODE_PRIVATE)
    }

    override fun loadExperimentLength(key: String, default: Double): Double {
        return prefs.getFloat(key, default.toFloat()).toDouble()
    }

    override fun storeExperimentLength(key: String, experimentLength: Double) {
        with (prefs.edit()) {
            putFloat(key, experimentLength.toFloat())
            apply()
        }
    }

}
