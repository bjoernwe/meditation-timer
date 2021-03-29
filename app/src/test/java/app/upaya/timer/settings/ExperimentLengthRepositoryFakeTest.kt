package app.upaya.timer.settings

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
    fun storeSessionLength() {

        // GIVEN a session length stored through TimerRepository
        val sessionLength = 3.1
        experimentLengthRepository.storeExperimentLength(sessionLength)

        // WHEN the session length is loaded again
        val loadedSessionLength = experimentLengthRepository.loadExperimentLength()

        // THEN it is the as before
        assertEquals(sessionLength, loadedSessionLength, 0.001)
    }

}
