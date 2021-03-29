package app.upaya.timer.session.repository

import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*


class SessionRepositoryFakeTest {

    @Test
    fun storeSession() = runBlocking {

        // GIVEN an empty SessionLogRepository
        val sessionRepository = ExperimentLogRepositoryFake()
        assert(sessionRepository.experiments.first().isEmpty())

        // WHEN a session is stored
        val sessionLog = ExperimentLog(hint = UUID.randomUUID())
        sessionRepository.storeExperiment(sessionLog)

        // THEN the session FlowState updates accordingly
        assert(sessionRepository.experiments.take(1).first()[0] == sessionLog)

    }

}
