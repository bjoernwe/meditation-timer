package app.upaya.timer.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import kotlin.concurrent.schedule


sealed class SessionState(
    /**
     * This state machine models the session's state transitions. It posts the current state to the
     * created MutableFlowState object. It also calls the SessionHandler on state transitions.
     **/
    protected val sessionHandler: ISessionHandler,
    protected val outputStateFlow: MutableStateFlow<SessionState?>,
    ) {

    companion object {

        fun create(sessionHandler: ISessionHandler): StateFlow<SessionState?> {

            sessionHandler.onSessionIdling()

            val stateFlow = MutableStateFlow<SessionState?>(null)
            val idleState = Idle(
                sessionHandler = sessionHandler,
                outputStateFlow = stateFlow,
            )
            stateFlow.value = idleState

            return stateFlow
        }

    }

}


class Idle internal constructor(
    sessionHandler: ISessionHandler,
    outputStateFlow: MutableStateFlow<SessionState?>
) : SessionState(
    sessionHandler = sessionHandler,
    outputStateFlow = outputStateFlow
) {

    fun startSession() {
        val runningState = Running(
            sessionHandler = sessionHandler,
            outputStateFlow = outputStateFlow
        )
        val sessionLength = sessionHandler.sessionLength.value.times(1000).toLong()
        Timer("SessionTimer", true).schedule(sessionLength) {
            runningState.onFinish()
        }
        sessionHandler.onSessionStarted()
        outputStateFlow.value = runningState
    }

}


class Running internal constructor(
    sessionHandler: ISessionHandler,
    outputStateFlow: MutableStateFlow<SessionState?>
) : SessionState(
    sessionHandler = sessionHandler,
    outputStateFlow = outputStateFlow
) {

    internal fun onFinish() {
        sessionHandler.onSessionFinished()
        val finishedState = Finished(
            sessionHandler = sessionHandler,
            outputStateFlow = outputStateFlow
        )
        outputStateFlow.value = finishedState
    }

}


class Finished internal constructor(
    sessionHandler: ISessionHandler,
    outputStateFlow: MutableStateFlow<SessionState?>
) : SessionState(
    sessionHandler = sessionHandler,
    outputStateFlow = outputStateFlow
) {

    fun rateSession(rating: SessionRating) {
        sessionHandler.onRatingSubmitted(rating = rating)
        sessionHandler.onSessionIdling()
        outputStateFlow.value = Idle(
            sessionHandler = sessionHandler,
            outputStateFlow = outputStateFlow
        )
    }

}
