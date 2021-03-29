package app.upaya.timer.experiments.repository.stats

import app.upaya.timer.experiments.repositories.logs.ExperimentLogRepositoryFake
import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import app.upaya.timer.experiments.repositories.stats.ExperimentStatsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class ExperimentStatsRepositoryTest {

    private val experimentLogRepository = ExperimentLogRepositoryFake()
    private val experimentStatsRepository = ExperimentStatsRepository(experimentLogRepository)

    private fun generateExperimentLog(length: Int) : ExperimentLog {
        return ExperimentLog(
            hint = UUID.randomUUID(),
            startDate = Date(0L),
            endDate = Date(length * 1000L)
        )
    }

    @Test
    fun experimentLiveDataStatistics() = runBlocking {

        // GIVEN an empty ExperimentRepository
        var experimentStats = experimentStatsRepository.experimentStats.first()
        assert(experimentStats.count == 0)
        assert(experimentStats.avgLength == null)
        assert(experimentStats.totalLength == null)

        // WHEN an experiment is added
        experimentLogRepository.storeExperiment(generateExperimentLog(length = 2))

        // THEN the corresponding Flow is updated accordingly
        experimentStats = experimentStatsRepository.experimentStats.first()
        assertEquals(2.0, experimentStats.avgLength!!, 0.001)
        assert(experimentStats.count == 1)
        assert(experimentStats.totalLength == 2)

        // AND WHEN another experiment is added
        experimentLogRepository.storeExperiment(generateExperimentLog(length = 4))

        // THEN the corresponding Flow is updated accordingly
        experimentStats = experimentStatsRepository.experimentStats.first()
        assertEquals(3.0, experimentStats.avgLength!!, 0.001)
        assert(experimentStats.count == 2)
        assert(experimentStats.totalLength == 6)
    }

}
