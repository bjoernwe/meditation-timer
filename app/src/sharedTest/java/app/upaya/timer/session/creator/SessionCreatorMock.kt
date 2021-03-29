package app.upaya.timer.session.creator

import app.upaya.timer.probes.Probe
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*


class SessionCreatorMock(
    private val onRatingSubmittedCalls: MutableList<ExperimentLog>,
    initialSessionLength: Double
) : ISessionCreator {

    private val _sessionLength = MutableStateFlow(initialSessionLength)
    override val sessionLength: StateFlow<Double> = _sessionLength

    private val _currentHint = MutableStateFlow(generateRandomHint())
    override val currentProbe: StateFlow<Probe> = _currentHint

    override fun onRatingSubmitted(experimentLog: ExperimentLog) {
        onRatingSubmittedCalls.add(experimentLog)
    }

    private fun generateRandomHint() : Probe {
        val randomUUID = UUID.randomUUID()
        return Probe(
            probe = randomUUID.toString(),
            id = randomUUID
        )
    }

}
