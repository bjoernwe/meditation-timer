package app.upaya.timer.session.viewmodel

import androidx.lifecycle.*
import app.upaya.timer.hints.Hint
import app.upaya.timer.session.*
import app.upaya.timer.session.creator.ISessionCreator
import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.stats.ISessionStatsRepository


class SessionViewModel(
    val sessionCreator: ISessionCreator,
    sessionRepository: ISessionRepository,
    sessionStatsRepository: ISessionStatsRepository
    ) : ViewModel() {

    /**
     * Session State
     */

    val state: LiveData<SessionState?> = SessionState.create(
        sessionCreator = sessionCreator,
        sessionRepository = sessionRepository,
    ).asLiveData()

    val isIdle: LiveData<Boolean> = Transformations.map(state) { it is Idle }
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it is Running }

    /**
     * Session Length
     */

    val sessionLength: LiveData<Double> = sessionCreator.sessionLength.asLiveData()
    val currentHint: LiveData<Hint> = sessionCreator.currentHint.asLiveData()

    /**
     * Session Stats
     */

    val sessionAggregate = sessionStatsRepository.sessionAggregate.asLiveData()
    val sessionAggregatesOfLastDays = sessionStatsRepository.sessionAggregatesOfLastDays.asLiveData()

}
