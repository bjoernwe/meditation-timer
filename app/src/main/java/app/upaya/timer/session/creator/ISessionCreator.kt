package app.upaya.timer.session.creator

import app.upaya.timer.probes.Probe
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.StateFlow


interface ISessionCreator {
    val sessionLength: StateFlow<Double>
    val currentProbe: StateFlow<Probe>
    fun onRatingSubmitted(experimentLog: ExperimentLog)
}
