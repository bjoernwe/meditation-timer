package app.upaya.timer.session

import androidx.lifecycle.MutableLiveData


sealed class SessionState(
    /**
     * This state machine models the session's state transitions. It takes LiveData objects to post
     * the current state and the number of remaining seconds to.
     **/
    protected val sessionHandler: SessionHandler,
    protected val sessionState: MutableLiveData<SessionState?>,
    protected val secondsRemaining: MutableLiveData<Int>
)


class Idle(sessionHandler: SessionHandler, sessionState: MutableLiveData<SessionState?>, secondsRemaining: MutableLiveData<Int>)
    : SessionState(sessionHandler, sessionState, secondsRemaining) {

    fun startSession() {
        val runningState = Running(sessionHandler, sessionState, secondsRemaining)
        sessionState.postValue(runningState)
        sessionHandler.start(
                onFinish = runningState::onFinish,
                onTick = runningState::onTick
        )
    }

}


class Running internal constructor(sessionHandler: SessionHandler, liveData: MutableLiveData<SessionState?>, secondsRemaining: MutableLiveData<Int>)
    : SessionState(sessionHandler, liveData, secondsRemaining) {

    internal fun onFinish() {
        sessionState.postValue(Finished(sessionHandler, sessionState, secondsRemaining))
        secondsRemaining.postValue(0)
    }

    internal fun onTick(secondsRemaining: Int) {
        this.secondsRemaining.postValue(secondsRemaining)
    }

}


class Finished internal constructor(sessionHandler: SessionHandler, liveData: MutableLiveData<SessionState?>, secondsRemaining: MutableLiveData<Int>)
    : SessionState(sessionHandler, liveData, secondsRemaining) {

    fun increaseSessionLength() {
        sessionHandler.increaseLength()
        sessionState.postValue(Idle(sessionHandler, sessionState, secondsRemaining))
    }

    fun decreaseSessionLength() {
        sessionHandler.decreaseLength()
        sessionState.postValue(Idle(sessionHandler, sessionState, secondsRemaining))
    }

}
