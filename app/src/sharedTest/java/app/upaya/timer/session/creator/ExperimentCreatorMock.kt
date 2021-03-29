package app.upaya.timer.session.creator

import app.upaya.timer.experiments.creator.IExperimentCreator
import app.upaya.timer.experiments.probes.Probe
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*


class ExperimentCreatorMock(
    private val onRatingSubmittedCalls: MutableList<ExperimentLog>,
    initialSessionLength: Double
) : IExperimentCreator {

    private val _sessionLength = MutableStateFlow(initialSessionLength)
    override val currentLength: StateFlow<Double> = _sessionLength

    private val _currentHint = MutableStateFlow(generateRandomHint())
    override val currentProbe: StateFlow<Probe> = _currentHint

    override fun onFeedbackSubmitted(experimentLog: ExperimentLog) {
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
