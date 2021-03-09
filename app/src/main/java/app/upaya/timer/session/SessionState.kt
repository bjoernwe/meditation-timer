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
    protected val sessionLength: Double,
    protected val sessionHandler: ISessionHandler,
    protected val currentState: MutableLiveData<SessionState>,
    ) {

    companion object {

        fun create(
            sessionHandler: ISessionHandler,
            initialSessionLength: Double = 10.0
        ): LiveData<SessionState> {

            val currentState = MutableLiveData<SessionState>()
            val currentStateImmutable: LiveData<SessionState> = currentState

            val idleState = Idle(
                sessionLength = initialSessionLength,
                sessionHandler = sessionHandler,
                currentState = currentState,
            )

            //sessionHandler.onSessionIdling()
            currentState.postValue(idleState)
            return currentStateImmutable
        }

    }

    fun getCurrentSessionLength() : Double {
        return sessionLength
    }

}

class Idle internal constructor(
    sessionLength: Double,
    sessionHandler: ISessionHandler,
    currentState: MutableLiveData<SessionState>,
) : SessionState(
    sessionLength = sessionLength,
    sessionHandler = sessionHandler,
    currentState = currentState,
) {

    fun startSession() {
        val nextState = Running(
            sessionLength = sessionLength,
            sessionHandler = sessionHandler,
            currentState = currentState)
        val sessionLength = sessionLength.toLong().times(1000L)
        Timer("SessionTimer", true).schedule(sessionLength) {
            nextState.onFinish()
        }
        currentState.postValue(nextState)
    }

}


class Running internal constructor(
    sessionLength: Double,
    sessionHandler: ISessionHandler,
    currentState: MutableLiveData<SessionState>,
) : SessionState(
    sessionLength = sessionLength,
    sessionHandler = sessionHandler,
    currentState = currentState,
) {

    internal fun onFinish() {
        val sessionLog = SessionLog(length = sessionLength.toInt())
        sessionHandler.onSessionFinished(sessionLog)
        val nextState = Finished(
            sessionLength = sessionLength,
            sessionHandler = sessionHandler,
            currentState = currentState
        )
        currentState.postValue(nextState)
    }

}


class Finished internal constructor(
    sessionLength: Double,
    sessionHandler: ISessionHandler,
    currentState: MutableLiveData<SessionState>,
) : SessionState(
    sessionLength = sessionLength,
    sessionHandler = sessionHandler,
    currentState = currentState,
) {

    fun rateSession(rating: SessionRating) : Double {
        val newSessionLength = sessionHandler.onRatingSubmitted(
            rating = rating,
            currentSessionLength = sessionLength
        )
        currentState.postValue(
            Idle(
                sessionLength = newSessionLength,
                sessionHandler = sessionHandler,
                currentState = currentState,
            )
        )
        return newSessionLength
    }

}
