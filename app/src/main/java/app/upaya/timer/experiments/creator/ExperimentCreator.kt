package app.upaya.timer.experiments.creator

import app.upaya.timer.experiments.probes.Probe
import app.upaya.timer.experiments.probes.ProbeRepository
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import app.upaya.timer.experiments.repositories.length.IExperimentLengthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ExperimentCreator(
    private val probeRepository: ProbeRepository,
    private val experimentLengthRepository: IExperimentLengthRepository,
    ) : IExperimentCreator {

    private val defaultExperimentLength = 10.0
    private val experimentLengths: ExperimentLengths = ExperimentLengths(
        defaultFactory = {
            ExperimentLength(initialLength = experimentLengthRepository.loadExperimentLength(it.toString()))
        }
    )

    private val _currentLength: MutableStateFlow<Double> = MutableStateFlow(defaultExperimentLength)
    override val currentLength: StateFlow<Double> = _currentLength

    private val _currentProbe: MutableStateFlow<Probe> = MutableStateFlow(probeRepository.getRandomProbe())
    override val currentProbe: StateFlow<Probe> = _currentProbe

    override fun onFeedbackSubmitted(experimentLog: ExperimentLog) {

        experimentLog.rating?.let {
            val newExperimentLength = experimentLengths.updateFromFeedback(
                hint = experimentLog.probeId,
                feedback = it.toDouble(),
            )
            experimentLengthRepository.storeExperimentLength(
                key = experimentLog.probeId.toString(),
                experimentLength = newExperimentLength,
            )
        }

        val newProbe = probeRepository.getRandomProbe()
        _currentProbe.value = newProbe
        _currentLength.value = experimentLengths[newProbe.id].value

    }

}
