package app.upaya.timer.experiments.repositories.length

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class ExperimentLengthRepositoryFakeTest {

    private lateinit var experimentLengthRepository: IExperimentLengthRepository

    @Before
    fun setUp() {
        experimentLengthRepository = ExperimentLengthRepositoryFake()
    }

    @Test
    fun storeExperimentLength() {

        // GIVEN a stored experiment length
        val experimentLength = 3.1
        experimentLengthRepository.storeExperimentLength(experimentLength)

        // WHEN the experiment length is loaded again
        val loadedExperimentLength = experimentLengthRepository.loadExperimentLength()

        // THEN it is the as before
        assertEquals(experimentLength, loadedExperimentLength, 0.001)
    }

}
