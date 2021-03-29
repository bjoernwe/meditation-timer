package app.upaya.timer.experiments.repository.length

import app.upaya.timer.experiments.repositories.length.ExperimentLengthRepositoryFake
import app.upaya.timer.experiments.repositories.length.IExperimentLengthRepository
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
