package app.upaya.timer.experiments.repositories.length

import android.content.Context


class ExperimentLengthRepository(context: Context, sharedPrefsName: String) : IExperimentLengthRepository {

    private val sharedPreferences = with (context.applicationContext) {
        getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
    }

    override fun loadExperimentLength(key: String, default: Double): Double {
        return sharedPreferences.getFloat(key, default.toFloat()).toDouble()
    }

    override fun storeExperimentLength(key: String, experimentLength: Double) {
        with (sharedPreferences.edit()) {
            putFloat(key, experimentLength.toFloat())
            apply()
        }
    }

}
