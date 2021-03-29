package app.upaya.timer.experiments.viewmodel

import androidx.lifecycle.*
import app.upaya.timer.probes.Probe
import app.upaya.timer.session.*
import app.upaya.timer.session.creator.ISessionCreator
import app.upaya.timer.experiments.repositories.logs.IExperimentLogRepository
import app.upaya.timer.experiments.repositories.stats.ExperimentStatsRepository


class ExperimentViewModel(
    sessionCreator: ISessionCreator,
    experimentLogRepository: IExperimentLogRepository,
    ) : ViewModel() {

    private val sessionStatsRepository = ExperimentStatsRepository(experimentLogRepository)

    /**
     * Experiment State
     */

    val state: LiveData<ExperimentState?> = ExperimentState.create(
        sessionCreator = sessionCreator,
        experimentLogRepository = experimentLogRepository,
    ).asLiveData()

    val experimentLength: LiveData<Double> = sessionCreator.sessionLength.asLiveData()

    val isIdle: LiveData<Boolean> = Transformations.map(state) { it is Idle }
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it is Running }

    /**
     * Probe
     */

    val currentProbe: LiveData<Probe> = sessionCreator.currentProbe.asLiveData()

    /**
     * Experiments Stats
     */

    val experimentStats = sessionStatsRepository.experimentStats.asLiveData()
    val experimentStatsOfLastDays = sessionStatsRepository.experimentStatsOfLastDays.asLiveData()

}
