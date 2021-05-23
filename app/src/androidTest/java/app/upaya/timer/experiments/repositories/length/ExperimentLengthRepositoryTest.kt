package app.upaya.timer.experiments.repositories.length

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class ExperimentLengthRepositoryTest {

    private lateinit var context: Context
    private lateinit var experimentLengthRepository: ExperimentLengthRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        experimentLengthRepository = ExperimentLengthRepository(context)
    }

    @Test
    fun storeExperimentLength() {

        // GIVEN a stored experiment length
        val prefKey = "experiment_length"
        val experimentLength = 3.1
        experimentLengthRepository.storeExperimentLength(key=prefKey, experimentLength = experimentLength)

        // WHEN the experiment length is loaded again
        val loadedExperimentLength = experimentLengthRepository.loadExperimentLength(key = prefKey)

        // THEN it is the as before
        assertEquals(experimentLength, loadedExperimentLength, 0.001)
    }

}
