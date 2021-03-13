package app.upaya.timer.session.viewmodel

import androidx.lifecycle.*
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

    private val _sessionLength = MutableLiveData(0.0)
    val sessionLength: LiveData<Double> = _sessionLength

    /**
     * Session Stats
     */

    val sessionAggregate = sessionStatsRepository.sessionAggregate.asLiveData()
    val sessionAggregatesOfLastDays = sessionStatsRepository.sessionAggregatesOfLastDays.asLiveData()

    /**
     * Event Handling
     * We use observeForever() because we don't want to have any LivecycleOwner in the ViewModel.
     * The observer is removed in onCleared().
     */

    private var stateObserver: Observer<SessionState?> = Observer(::onTimerStateChanged)
    init { state.observeForever(stateObserver) }

    /**
     * Update LiveData on State Changes
     */

    private fun onTimerStateChanged(newState: SessionState?) {
        when (newState) {
            is Idle -> { _sessionLength.postValue(sessionHandler.sessionLength) }
            is Running -> {}
            is Finished -> {}
        }
    }

    // ViewModel destructor
    override fun onCleared() {
        super.onCleared()
        state.removeObserver(stateObserver)
    }

}
