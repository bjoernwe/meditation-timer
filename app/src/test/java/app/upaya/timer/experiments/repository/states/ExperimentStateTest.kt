package app.upaya.timer.experiments.repository.states

import app.upaya.timer.experiments.ExperimentState
import app.upaya.timer.experiments.Finished
import app.upaya.timer.experiments.Idle
import app.upaya.timer.experiments.Running
import app.upaya.timer.experiments.creator.IExperimentCreator
import app.upaya.timer.experiments.repositories.creator.ExperimentCreatorMock
import app.upaya.timer.experiments.repositories.logs.IExperimentLogRepository
import app.upaya.timer.experiments.repositories.logs.ExperimentLogRepositoryFake
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*


class ExperimentStateTest {

    @Test
    fun experimentStateTransitions() = runBlocking {

        // GIVEN a ExperimentState object
        val experimentCreator: IExperimentCreator = ExperimentCreatorMock(
            feedbackSubmittedCalls = ArrayList(),
            initialExperimentLength = 2.0
        )
        val state: StateFlow<ExperimentState?> = ExperimentState.create(
            experimentCreator = experimentCreator,
            experimentLogRepository = ExperimentLogRepositoryFake(),
        )

        // WHEN an experiment is started
        (state.first() as Idle).startExperiment()

        // THEN the state moves from Idle to Running
        assert(state.first() is Running)

        // AND WHEN one second has passed
        Thread.sleep(1000)

        // THEN the state is still Running
        assert(state.first() is Running)

        // AND WHEN more time has passed
        Thread.sleep(1500)

        // THEN the state is Finished
        assert(state.first() is Finished)

        // AND WHEN the finished experiment is rated
        (state.first() as Finished).rateExperiment(0.0)

        // THEN the new state is Idle again
        assert(state.first() is Idle)
    }

    @Test
    fun experimentLogsAreStoredWithTimestamps() = runBlocking {

        // GIVEN an ExperimentState object
        val experimentCreator: IExperimentCreator = ExperimentCreatorMock(
            feedbackSubmittedCalls = ArrayList(),
            initialExperimentLength = 1.0
        )
        val experimentLogRepository: IExperimentLogRepository = ExperimentLogRepositoryFake()
        val state: StateFlow<ExperimentState?> = ExperimentState.create(
            experimentCreator = experimentCreator,
            experimentLogRepository = experimentLogRepository,
        )

        val storedExperiment = experimentLogRepository.lastExperiment.first()
        assert(storedExperiment.startDate == null)
        assert(storedExperiment.endDate == null)
        assert(storedExperiment.ratingDate == null)

        // WHEN an experiment is started
        (state.first() as Idle).startExperiment()

        // THEN the stored experiment has a start timestamp
        assert(storedExperiment.startDate != null)
        assert(storedExperiment.endDate == null)
        assert(storedExperiment.ratingDate == null)

        // AND WHEN the experiment has finished
        state.first { s -> s is Finished }

        // THEN the stored experiment has an end timestamp
        assert(storedExperiment.startDate != null)
        assert(storedExperiment.endDate != null)
        assert(storedExperiment.ratingDate == null)

        // AND WHEN the finished experiment is rated
        (state.first() as Finished).rateExperiment(0.0)

        // THEN the stored experiment has an end timestamp
        assert(storedExperiment.startDate != null)
        assert(storedExperiment.endDate != null)
        assert(storedExperiment.ratingDate != null)

        // AND THEN a new idling experiment is stored already
        // THEN the stored experiment has an end timestamp
        val newStoredExperiment = experimentLogRepository.lastExperiment.first()
        assert(newStoredExperiment.startDate == null)
        assert(newStoredExperiment.endDate == null)
        assert(newStoredExperiment.ratingDate == null)
    }

}
