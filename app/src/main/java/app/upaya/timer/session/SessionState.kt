package app.upaya.timer.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.concurrent.schedule


sealed class SessionState(
    /**
     * This state machine models the session's state transitions. It posts the current state to the
     * given MutableLiveData object. It also calls the SessionHandler on state transitions.
     **/
    protected val sessionHandler: SessionHandler,
    protected val currentState: MutableLiveData<SessionState>,
    ) {

    companion object {

        fun create(sessionHandler: SessionHandler): LiveData<SessionState> {

            val currentState = MutableLiveData<SessionState>()
            val currentStateImmutable: LiveData<SessionState> = currentState

            val idleState = Idle(
                sessionHandler = sessionHandler,
                currentState = currentState,
            )

            sessionHandler.onSessionIdling()
            currentState.postValue(idleState)
            return currentStateImmutable
        }

    }

}

class Idle internal constructor(
    sessionHandler: SessionHandler,
    currentState: MutableLiveData<SessionState>,
) : SessionState(
    sessionHandler = sessionHandler,
    currentState = currentState,
) {

    fun startSession() {
        val nextState = Running(
            sessionHandler = sessionHandler,
            currentState = currentState)
        val sessionLength = sessionHandler.sessionLength.toLong().times(1000L)
        Timer("SessionTimer", true).schedule(sessionLength) {
            nextState.onFinish()
        }
        currentState.postValue(nextState)
    }

}


class Running internal constructor(
    sessionHandler: SessionHandler,
    currentState: MutableLiveData<SessionState>,
) : SessionState(
    sessionHandler = sessionHandler,
    currentState = currentState,
) {

    internal fun onFinish() {
        val nextState = Finished(
            sessionHandler = sessionHandler,
            currentState = currentState
        )
        currentState.postValue(nextState)
    }

}


enum class SessionRating {
    UP, DOWN
}


class Finished internal constructor(
    sessionHandler: SessionHandler,
    currentState: MutableLiveData<SessionState>,
) : SessionState(
    sessionHandler = sessionHandler,
    currentState = currentState,
) {

    fun rateSession(rating: SessionRating) : Double {
        val newSessionLength = sessionHandler.onRatingSubmitted(rating)
        currentState.postValue(
            Idle(
                sessionHandler = sessionHandler,
                currentState = currentState,
            )
        )
        return newSessionLength
    }

}
