package app.upaya.timer.experiments.viewmodel

import androidx.lifecycle.*
import app.upaya.timer.experiments.ExperimentState
import app.upaya.timer.experiments.Idle
import app.upaya.timer.experiments.Running
import app.upaya.timer.experiments.probes.Probe
import app.upaya.timer.experiments.creator.IExperimentCreator
import app.upaya.timer.experiments.repositories.logs.IExperimentLogRepository
import app.upaya.timer.experiments.repositories.stats.ExperimentStatsRepository


class ExperimentViewModel(
    experimentCreator: IExperimentCreator,
    experimentLogRepository: IExperimentLogRepository,
    ) : ViewModel() {

    private val experimentStatsRepository = ExperimentStatsRepository(experimentLogRepository)

    /**
     * Experiment State
     */

    val state: LiveData<ExperimentState?> = ExperimentState.create(
        experimentCreator = experimentCreator,
        experimentLogRepository = experimentLogRepository,
    ).asLiveData()

    val experimentLength: LiveData<Double> = experimentCreator.currentLength.asLiveData()

    val isIdle: LiveData<Boolean> = Transformations.map(state) { it is Idle }
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it is Running }

    /**
     * Probe
     */

    val currentProbe: LiveData<Probe> = experimentCreator.currentProbe.asLiveData()

    /**
     * Experiments Stats
     */

    val experimentStats = experimentStatsRepository.experimentStats.asLiveData()
    val experimentStatsOfLastDays = experimentStatsRepository.experimentStatsOfLastDays.asLiveData()

}
