package app.upaya.timer.session.viewmodel

import androidx.lifecycle.*
import app.upaya.timer.hints.Hint
import app.upaya.timer.session.*
import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.SessionRepository
import app.upaya.timer.session.repository.stats.ISessionStatsRepository
import app.upaya.timer.session.repository.stats.SessionStatsRepository


class SessionViewModel(
    sessionHandler: ISessionHandler,
    sessionRepository: ISessionRepository,
    ) : ViewModel() {

    private val sessionStatsRepository = SessionStatsRepository(sessionRepository)

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
