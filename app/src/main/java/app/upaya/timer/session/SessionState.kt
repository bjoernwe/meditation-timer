package app.upaya.timer.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


sealed class SessionState(
    /**
     * This state machine models the session's state transitions. It takes LiveData objects to post
     * the current state to.
     **/
    protected val sessionHandler: SessionHandler,
    protected val _currentState: MutableLiveData<SessionState>,
    protected val currentState: LiveData<SessionState>,
    ) {

    companion object {

        fun create(sessionHandler: SessionHandler): LiveData<SessionState> {

            val currentStateMutable = MutableLiveData<SessionState>()
            val currentState: LiveData<SessionState> = currentStateMutable

            val initialState = Idle(
                sessionHandler = sessionHandler,
                _currentState = currentStateMutable,
                currentState = currentState
            )

            currentStateMutable.postValue(initialState)
            return currentState
        }

    }

}

    class Idle internal constructor(
        sessionHandler: SessionHandler,
        _currentState: MutableLiveData<SessionState>,
        currentState: LiveData<SessionState>,
    ) : SessionState(
        sessionHandler = sessionHandler,
        _currentState = _currentState,
        currentState = currentState,
    ) {

        fun startSession() {
            val runningState = Running(
                sessionHandler = sessionHandler,
                _currentState = _currentState,
                currentState = currentState)
            _currentState.postValue(runningState)
            sessionHandler.start(
                onFinish = runningState::onFinish,
                onTick = runningState::onTick
            )
        }

    }


    class Running internal constructor(
        sessionHandler: SessionHandler,
        _currentState: MutableLiveData<SessionState>,
        currentState: LiveData<SessionState>,
    ) : SessionState(
        sessionHandler = sessionHandler,
        _currentState = _currentState,
        currentState = currentState
    ) {

        internal fun onFinish() {
            _currentState.postValue(
                Finished(
                    sessionHandler = sessionHandler,
                    _currentState = _currentState,
                    currentState = currentState
                )
            )
        }

        internal fun onTick(secondsRemaining: Int) { }

    }


    class Finished internal constructor(
        sessionHandler: SessionHandler,
        _currentState: MutableLiveData<SessionState>,
        currentState: LiveData<SessionState>,
    ) : SessionState(
        sessionHandler = sessionHandler,
        _currentState = _currentState,
        currentState = currentState
    ) {

        fun increaseSessionLength() {
            sessionHandler.increaseLength()
            _currentState.postValue(
                Idle(
                    sessionHandler = sessionHandler,
                    _currentState = _currentState,
                    currentState = currentState,
                )
            )
        }

        fun decreaseSessionLength() {
            sessionHandler.decreaseLength()
            _currentState.postValue(
                Idle(
                    sessionHandler = sessionHandler,
                    _currentState = _currentState,
                    currentState = currentState,
                )
            )
        }

    }
