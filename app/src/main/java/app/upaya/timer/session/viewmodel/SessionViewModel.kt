package app.upaya.timer.session.viewmodel

import androidx.lifecycle.*
import app.upaya.timer.session.*
import app.upaya.timer.session.repository.stats.ISessionStatsRepository
import app.upaya.timer.session.repository.stats.SessionAggregate
import kotlinx.coroutines.launch


class SessionViewModel(
    val sessionHandler: ISessionHandler,
    private val sessionStatsRepository: ISessionStatsRepository
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

    private val _sessionAggOfAll: MutableLiveData<SessionAggregate> = MutableLiveData()
    private val _sessionAggOfLastDays: MutableLiveData<List<SessionAggregate>> = MutableLiveData()
    val sessionAggOfAll: LiveData<SessionAggregate> = _sessionAggOfAll
    val sessionAggOfLastDays: LiveData<List<SessionAggregate>> = _sessionAggOfLastDays

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
            is Idle -> {
                viewModelScope.launch {
                    _sessionAggOfAll.value = sessionStatsRepository.getSessionAggregate()
                    _sessionAggOfLastDays.value = sessionStatsRepository.getSessionAggregateOfLastDays()
                }
                _sessionLength.postValue(sessionHandler.sessionLength)
            }
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
