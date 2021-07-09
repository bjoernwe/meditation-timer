package app.upaya.timer.experiments.creator

import app.upaya.timer.experiments.probes.Probe
import app.upaya.timer.experiments.probes.ProbeRepository
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import app.upaya.timer.experiments.repositories.length.IExperimentLengthRepository
import kotlinx.coroutines.flow.*
import java.util.*


class ExperimentCreator(
    private val probeRepository: ProbeRepository,
    private val experimentLengthRepository: IExperimentLengthRepository,
    ) : IExperimentCreator {

    private val defaultExperimentLength = 10.0

    private val _currentLength: MutableStateFlow<Double> = MutableStateFlow(defaultExperimentLength)
    override val currentLength: StateFlow<Double> = _currentLength

    private val _currentProbe: MutableStateFlow<Probe> = MutableStateFlow(probeRepository.getRandomProbe())
    override val currentProbe: StateFlow<Probe> = _currentProbe

    override fun onFeedbackSubmitted(experimentLog: ExperimentLog) {
        storeUpdatedExperimentLength(experimentLog = experimentLog)
        val nextProbe = calcNextProbe()
        _currentProbe.value = nextProbe
        _currentLength.value = calcNextExperimentLength(nextProbe.id)
    }

    private fun storeUpdatedExperimentLength(experimentLog: ExperimentLog) {
        experimentLog.rating?.let { rating ->
            val feedback = rating.toDouble()
            val newExperimentLength = ExperimentLength(_currentLength.value).updateFromFeedback(feedback)
            experimentLengthRepository.storeExperimentLength(
                key = experimentLog.probeId.toString(),
                experimentLength = newExperimentLength,
            )
        }
    }

    private fun calcNextProbe(): Probe {
        return probeRepository.getRandomProbe()
    }

    private fun calcNextExperimentLength(probeId: UUID): Double {
        return experimentLengthRepository.loadExperimentLength(
            key = probeId.toString(),
            default = defaultExperimentLength
        )
    }

}
