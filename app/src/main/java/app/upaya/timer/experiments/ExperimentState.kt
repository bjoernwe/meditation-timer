package app.upaya.timer.session

import app.upaya.timer.experiments.creator.IExperimentCreator
import app.upaya.timer.experiments.repositories.logs.IExperimentLogRepository
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import kotlin.concurrent.schedule


sealed class ExperimentState(
    /**
     * This state machine models the experiment's state transitions. It posts the current state to
     * the created [MutableFlowState] object. It also updates and stores experiment logs. Details
     * about the experiments - like their length and the next probe - are deferred to an injected
     * [SessionCreator] object, which may be provide different implementations.
     **/
    protected val experimentLog: ExperimentLog,
    protected val experimentCreator: IExperimentCreator,
    protected val experimentLogRepository: IExperimentLogRepository,
    protected val outputStateFlow: MutableStateFlow<ExperimentState?>,
    ) {

    init {
        // store the current session on every state transition
        experimentLogRepository.storeExperiment(experimentLog = experimentLog)
    }

    companion object {

        fun create(
            experimentCreator: IExperimentCreator,
            experimentLogRepository: IExperimentLogRepository,
        ): StateFlow<ExperimentState?> {
            val outputStateFlow = MutableStateFlow<ExperimentState?>(null)
            outputStateFlow.value = Idle(
                experimentLog = ExperimentLog(hint = experimentCreator.currentProbe.value.id),
                experimentCreator = experimentCreator,
                experimentLogRepository = experimentLogRepository,
                outputStateFlow = outputStateFlow,
            )
            return outputStateFlow
        }

    }

}


class Idle internal constructor(
    experimentLog: ExperimentLog,
    experimentCreator: IExperimentCreator,
    experimentLogRepository: IExperimentLogRepository,
    outputStateFlow: MutableStateFlow<ExperimentState?>
) : ExperimentState(
    experimentLog = experimentLog,
    experimentCreator = experimentCreator,
    experimentLogRepository = experimentLogRepository,
    outputStateFlow = outputStateFlow
) {

    fun startExperiment() {
        val runningState = Running(
            experimentLog = experimentLog.apply { this.startDate = Date() },
            experimentCreator = experimentCreator,
            experimentLogRepository = experimentLogRepository,
            outputStateFlow = outputStateFlow
        )
        val sessionLength = experimentCreator.currentLength.value.times(1000).toLong()
        Timer("SessionTimer", true).schedule(sessionLength) {
            runningState.onFinish()
        }
        outputStateFlow.value = runningState
    }

}


class Running internal constructor(
    experimentLog: ExperimentLog,
    experimentCreator: IExperimentCreator,
    experimentLogRepository: IExperimentLogRepository,
    outputStateFlow: MutableStateFlow<ExperimentState?>
) : ExperimentState(
    experimentLog = experimentLog,
    experimentCreator = experimentCreator,
    experimentLogRepository = experimentLogRepository,
    outputStateFlow = outputStateFlow
) {

    internal fun onFinish() {
        outputStateFlow.value = Finished(
            experimentLog = experimentLog.apply { this.endDate = Date() },
            experimentCreator = experimentCreator,
            experimentLogRepository = experimentLogRepository,
            outputStateFlow = outputStateFlow
        )
    }

}


class Finished internal constructor(
    experimentLog: ExperimentLog,
    experimentCreator: IExperimentCreator,
    experimentLogRepository: IExperimentLogRepository,
    outputStateFlow: MutableStateFlow<ExperimentState?>
) : ExperimentState(
    experimentLog = experimentLog,
    experimentCreator = experimentCreator,
    experimentLogRepository = experimentLogRepository,
    outputStateFlow = outputStateFlow
) {

    fun rateExperiment(rating: Double) {

        // update & store session rating
        experimentLog.ratingDate = Date()
        experimentLog.rating = rating.toFloat()
        experimentLogRepository.storeExperiment(experimentLog = experimentLog)

        // inform SessionCreator
        experimentCreator.onFeedbackSubmitted(experimentLog = experimentLog)

        // update StateFlow
        outputStateFlow.value = Idle(
            experimentLog = ExperimentLog(hint = experimentCreator.currentProbe.value.id),
            experimentCreator = experimentCreator,
            experimentLogRepository = experimentLogRepository,
            outputStateFlow = outputStateFlow
        )

    }

}
