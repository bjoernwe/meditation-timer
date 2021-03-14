package app.upaya.timer.session.viewmodel

import androidx.lifecycle.*
import app.upaya.timer.hints.Hint
import app.upaya.timer.session.*
import app.upaya.timer.session.repository.stats.ISessionStatsRepository


class SessionViewModel(
    val sessionHandler: ISessionHandler,
    sessionStatsRepository: ISessionStatsRepository
    ) : ViewModel() {

    /**
     * Session State
     */

    val state: LiveData<SessionState?> = SessionState.create(sessionHandler).asLiveData()
    val isIdle: LiveData<Boolean> = Transformations.map(state) { it is Idle }
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it is Running }

    /**
     * Session Length
     */

    val sessionLength: LiveData<Double> = sessionHandler.sessionLength.asLiveData()
    val currentHint: LiveData<Hint> = sessionHandler.currentHint.asLiveData()

    /**
     * Session Stats
     */

    val sessionAggregate = sessionStatsRepository.sessionAggregate.asLiveData()
    val sessionAggregatesOfLastDays = sessionStatsRepository.sessionAggregatesOfLastDays.asLiveData()

}
