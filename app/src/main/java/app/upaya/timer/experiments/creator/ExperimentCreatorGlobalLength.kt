package app.upaya.timer.experiments.creator

import app.upaya.timer.experiments.probes.Probe
import app.upaya.timer.experiments.probes.ProbeRepository
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import app.upaya.timer.experiments.repositories.length.IExperimentLengthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ExperimentCreatorGlobalLength(
    private val probeRepository: ProbeRepository,
    private val experimentLengthRepository: IExperimentLengthRepository,
    ) : IExperimentCreator {

    private val prefKey = "experiment_length"

    private val experimentLength = ExperimentLength(experimentLengthRepository.loadExperimentLength(key=prefKey))
    private val _experimentLength: MutableStateFlow<Double> = MutableStateFlow(experimentLength.value)
    override val currentLength: StateFlow<Double> = _experimentLength

    private val _currentProbe: MutableStateFlow<Probe> = MutableStateFlow(probeRepository.getRandomProbe())
    override val currentProbe: StateFlow<Probe> = _currentProbe

    override fun onFeedbackSubmitted(experimentLog: ExperimentLog) {
        _currentProbe.value = probeRepository.getRandomProbe()
        experimentLog.rating?.let {
            val newExperimentLength = experimentLength.updateFromFeedback(it.toDouble())
            _experimentLength.value = newExperimentLength
            experimentLengthRepository.storeExperimentLength(
                key = prefKey,
                experimentLength = newExperimentLength
            )
        }
    }

}
