package app.upaya.timer.experiments.repositories.creator

import app.upaya.timer.experiments.creator.IExperimentCreator
import app.upaya.timer.experiments.probes.Probe
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*


class ExperimentCreatorMock(
    private val feedbackSubmittedCalls: MutableList<ExperimentLog>,
    initialExperimentLength: Double
) : IExperimentCreator {

    private val _currentLength = MutableStateFlow(initialExperimentLength)
    override val currentLength: StateFlow<Double> = _currentLength

    private val _currentProbe = MutableStateFlow(generateRandomProbe())
    override val currentProbe: StateFlow<Probe> = _currentProbe

    override fun onFeedbackSubmitted(experimentLog: ExperimentLog) {
        feedbackSubmittedCalls.add(experimentLog)
    }

    private fun generateRandomProbe() : Probe {
        val randomUUID = UUID.randomUUID()
        return Probe(
            probe = randomUUID.toString(),
            id = randomUUID
        )
    }

}
