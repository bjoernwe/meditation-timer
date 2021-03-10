package app.upaya.timer.session

import androidx.lifecycle.*


class SessionViewModel(
    sessionHandler: SessionHandler,
    initialSessionLength: Double
) : ViewModel() {

    /**
     * Session LiveData
     */

    val state: LiveData<SessionState> = SessionState.create(
        sessionHandler = sessionHandler,
        initialSessionLength = initialSessionLength
    )

    private val _sessionLength = MutableLiveData(0.0)
    val sessionLength: LiveData<Double> = _sessionLength

    // Event Handling
    // We use observeForever() because we don't want to have any LivecycleOwner in the ViewModel.
    // The observer is removed in onCleared().
    private var stateObserver: Observer<SessionState> = Observer(::onTimerStateChanged)
    init { state.observeForever(stateObserver) }

    // Transformations
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it is Running }
    val isIdle: LiveData<Boolean> = Transformations.map(state) { it is Idle }

    private fun onTimerStateChanged(newState: SessionState) {
        when (newState) {
            is Idle -> { _sessionLength.postValue(newState.getCurrentSessionLength()) }
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
