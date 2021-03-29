package app.upaya.timer.session.creator

import app.upaya.timer.probes.Probe
import app.upaya.timer.probes.ProbeRepository
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import app.upaya.timer.settings.SessionLengthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SessionCreator(
    private val probeRepository: ProbeRepository,
    private val sessionLengthRepository: SessionLengthRepository,
    ) : ISessionCreator {

    private val sessionLengthUpdater = SessionLength(sessionLengthRepository.loadSessionLength())
    private val _sessionLength: MutableStateFlow<Double> = MutableStateFlow(sessionLengthUpdater.value)
    override val sessionLength: StateFlow<Double> = _sessionLength

    private val _currentProbe: MutableStateFlow<Probe> = MutableStateFlow(probeRepository.getRandomProbe())
    override val currentProbe: StateFlow<Probe> = _currentProbe

    override fun onRatingSubmitted(experimentLog: ExperimentLog) {
        _currentProbe.value = probeRepository.getRandomProbe()
        experimentLog.rating?.let {
            val newSessionLength = sessionLengthUpdater.updateFromRating(it.toDouble())
            _sessionLength.value = newSessionLength
            sessionLengthRepository.storeSessionLength(newSessionLength)
        }
    }

}
