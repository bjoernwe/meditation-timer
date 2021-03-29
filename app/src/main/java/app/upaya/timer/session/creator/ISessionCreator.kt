package app.upaya.timer.session.creator

import app.upaya.timer.probes.Probe
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.StateFlow


interface ISessionCreator {
    val sessionLength: StateFlow<Double>
    val currentProbe: StateFlow<Probe>
    fun onRatingSubmitted(sessionLog: SessionLog)
}
