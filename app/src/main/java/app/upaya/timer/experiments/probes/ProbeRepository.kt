package app.upaya.timer.experiments.probes

import android.content.Context
import app.upaya.timer.R


class ProbeRepository(private val context: Context) {

    fun getRandomProbe() : Probe {
        val probe: String = context.resources.getStringArray(R.array.probes).random()
        return Probe(probe = probe)
    }

}
