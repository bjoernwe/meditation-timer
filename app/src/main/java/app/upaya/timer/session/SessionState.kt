package app.upaya.timer.session

import app.upaya.timer.session.creator.ISessionCreator
import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import kotlin.concurrent.schedule


sealed class SessionState(
    /**
     * This state machine models the session's state transitions. It posts the current state to the
     * created MutableFlowState object.
     **/
    protected val sessionLog: SessionLog,
    protected val sessionCreator: ISessionCreator,
    protected val sessionRepository: ISessionRepository,
    protected val outputStateFlow: MutableStateFlow<SessionState?>,
    ) {

    init {
        // store the current session on every state transition
        sessionRepository.storeSession(sessionLog = sessionLog)
    }

    companion object {

        fun create(
            sessionCreator: ISessionCreator,
            sessionRepository: ISessionRepository,
        ): StateFlow<SessionState?> {
            val outputStateFlow = MutableStateFlow<SessionState?>(null)
            val idleState = Idle(
                sessionLog = SessionLog(hint = sessionCreator.currentHint.value.id),
                sessionCreator = sessionCreator,
                sessionRepository = sessionRepository,
                outputStateFlow = outputStateFlow,
            )
            outputStateFlow.value = idleState
            return outputStateFlow
        }

    }

}


class Idle internal constructor(
    sessionLog: SessionLog,
    sessionCreator: ISessionCreator,
    sessionRepository: ISessionRepository,
    outputStateFlow: MutableStateFlow<SessionState?>
) : SessionState(
    sessionLog = sessionLog,
    sessionCreator = sessionCreator,
    sessionRepository = sessionRepository,
    outputStateFlow = outputStateFlow
) {

    fun startSession() {
        val runningState = Running(
            sessionLog = sessionLog.apply { this.startDate = Date() },
            sessionCreator = sessionCreator,
            sessionRepository = sessionRepository,
            outputStateFlow = outputStateFlow
        )
        val sessionLength = sessionCreator.sessionLength.value.times(1000).toLong()
        Timer("SessionTimer", true).schedule(sessionLength) {
            runningState.onFinish()
        }
        outputStateFlow.value = runningState
    }

}


class Running internal constructor(
    sessionLog: SessionLog,
    sessionCreator: ISessionCreator,
    sessionRepository: ISessionRepository,
    outputStateFlow: MutableStateFlow<SessionState?>
) : SessionState(
    sessionLog = sessionLog,
    sessionCreator = sessionCreator,
    sessionRepository = sessionRepository,
    outputStateFlow = outputStateFlow
) {

    internal fun onFinish() {
        val finishedState = Finished(
            sessionLog = sessionLog.apply { this.endDate = Date() },
            sessionCreator = sessionCreator,
            sessionRepository = sessionRepository,
            outputStateFlow = outputStateFlow
        )
        outputStateFlow.value = finishedState
    }

}


class Finished internal constructor(
    sessionLog: SessionLog,
    sessionCreator: ISessionCreator,
    sessionRepository: ISessionRepository,
    outputStateFlow: MutableStateFlow<SessionState?>
) : SessionState(
    sessionLog = sessionLog,
    sessionCreator = sessionCreator,
    sessionRepository = sessionRepository,
    outputStateFlow = outputStateFlow
) {

    fun rateSession(rating: Double) {

        sessionLog.ratingDate = Date()
        sessionLog.rating = rating.toFloat()
        sessionRepository.storeSession(sessionLog = sessionLog)

        sessionCreator.onRatingSubmitted(sessionLog = sessionLog)

        outputStateFlow.value = Idle(
            sessionLog = SessionLog(hint = sessionCreator.currentHint.value.id),
            sessionCreator = sessionCreator,
            sessionRepository = sessionRepository,
            outputStateFlow = outputStateFlow
        )

    }

}
