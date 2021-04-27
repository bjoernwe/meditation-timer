package app.upaya.timer.experiments.creator

import app.upaya.timer.experiments.probes.Probe
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.StateFlow


interface IExperimentCreator {
    val currentLength: StateFlow<Double>
    val currentProbe: StateFlow<Probe>
    fun onFeedbackSubmitted(experimentLog: ExperimentLog)
}
