package app.upaya.timer.experiments.viewmodel

import androidx.lifecycle.*
import app.upaya.timer.probes.Probe
import app.upaya.timer.session.*
import app.upaya.timer.session.creator.ISessionCreator
import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.stats.SessionStatsRepository


class ExperimentViewModel(
    sessionCreator: ISessionCreator,
    sessionRepository: ISessionRepository,
    ) : ViewModel() {

    private val sessionStatsRepository = SessionStatsRepository(sessionRepository)

    /**
     * Experiment State
     */

    val state: LiveData<SessionState?> = SessionState.create(
        sessionCreator = sessionCreator,
        sessionRepository = sessionRepository,
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
