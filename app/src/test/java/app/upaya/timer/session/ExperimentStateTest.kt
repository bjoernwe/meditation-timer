package app.upaya.timer.session

import app.upaya.timer.experiments.creator.IExperimentCreator
import app.upaya.timer.session.creator.ExperimentCreatorMock
import app.upaya.timer.experiments.repositories.logs.IExperimentLogRepository
import app.upaya.timer.session.repository.ExperimentLogRepositoryFake
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*


class ExperimentStateTest {

    @Test
    fun sessionStateTransitions() = runBlocking {

        // GIVEN a SessionState object
        val experimentCreator: IExperimentCreator = ExperimentCreatorMock(
            onRatingSubmittedCalls = ArrayList(),
            initialSessionLength = 2.0
        )
        val state: StateFlow<ExperimentState?> = ExperimentState.create(
            experimentCreator = experimentCreator,
            experimentLogRepository = ExperimentLogRepositoryFake(),
        )

        // WHEN a session is started
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

        // AND WHEN the finished session is rated
        (state.first() as Finished).rateExperiment(0.0)

        // THEN the new state is Idle again
        assert(state.first() is Idle)
    }

    @Test
    fun sessionLogsAreStoredWithTimestamps() = runBlocking {

        // GIVEN a SessionState object
        val experimentCreator: IExperimentCreator = ExperimentCreatorMock(
            onRatingSubmittedCalls = ArrayList(),
            initialSessionLength = 1.0
        )
        val experimentLogRepository: IExperimentLogRepository = ExperimentLogRepositoryFake()
        val state: StateFlow<ExperimentState?> = ExperimentState.create(
            experimentCreator = experimentCreator,
            experimentLogRepository = experimentLogRepository,
        )

        val storedSession = experimentLogRepository.lastExperiment.first()
        assert(storedSession.startDate == null)
        assert(storedSession.endDate == null)
        assert(storedSession.ratingDate == null)

        // WHEN a session is started
        (state.first() as Idle).startExperiment()

        // THEN the stored session has a start timestamp
        assert(storedSession.startDate != null)
        assert(storedSession.endDate == null)
        assert(storedSession.ratingDate == null)

        // AND WHEN the session has finished
        state.first { s -> s is Finished }

        // THEN the stored session has an end timestamp
        assert(storedSession.startDate != null)
        assert(storedSession.endDate != null)
        assert(storedSession.ratingDate == null)

        // AND WHEN the finished session is rated
        (state.first() as Finished).rateExperiment(0.0)

        // THEN the stored session has an end timestamp
        assert(storedSession.startDate != null)
        assert(storedSession.endDate != null)
        assert(storedSession.ratingDate != null)

        // AND THEN a new idling session is stored already
        // THEN the stored session has an end timestamp
        val newStoredSession = experimentLogRepository.lastExperiment.first()
        assert(newStoredSession.startDate == null)
        assert(newStoredSession.endDate == null)
        assert(newStoredSession.ratingDate == null)
    }

}
