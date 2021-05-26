package app.upaya.timer.experiments.repositories.logs

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*


class ExperimentLogRepositoryFakeTest {

    @Test
    fun storeExperimentLog() = runBlocking {

        // GIVEN an empty ExperimentLogRepository
        val experimentLogRepository = ExperimentLogRepositoryFake()
        assert(experimentLogRepository.experiments.first().isEmpty())

        // WHEN an experiment is stored
        val experimentLog = ExperimentLog(probeId = UUID.randomUUID())
        experimentLogRepository.storeExperiment(experimentLog)

        // THEN the experiment's FlowState updates accordingly
        assert(experimentLogRepository.experiments.take(1).first()[0] == experimentLog)

    }

}
