package app.upaya.timer.experiments.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.MeditationTimerApplication
import app.upaya.timer.R
import app.upaya.timer.experiments.creator.ExperimentCreator
import app.upaya.timer.experiments.probes.ProbeRepository
import app.upaya.timer.experiments.repositories.logs.ExperimentLogRepository
import app.upaya.timer.experiments.repositories.logs.room.ExperimentLogDatabase
import app.upaya.timer.experiments.repositories.length.ExperimentLengthRepository
import java.lang.IllegalArgumentException


/**
 * The factory allows us to pass arguments to the ExperimentViewModel.
 * It is also used as a "dirty main component" that assembles and injects all the dependencies for
 * the business layer.
 * */
class ExperimentViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ExperimentViewModel::class.java)) {

            /**
             * Create & Inject Dependencies
             */

            // ExperimentLogRepository
            val experimentLogDatabase = ExperimentLogDatabase.getInstance(context)
            val experimentLogRepository = ExperimentLogRepository(
                experimentLogDao = experimentLogDatabase.experimentLogDao,
                externalScope = (context.applicationContext as MeditationTimerApplication).applicationScope
            )

            // ProbeRepository
            val probeRepository = ProbeRepository(context)

            // ExperimentRepository
            val experimentLengthRepository = ExperimentLengthRepository(
                context = context,
                sharedPrefsName = context.getString(R.string.experiment_length_pref_file)
            )

            // ExperimentCreator
            val experimentCreator = ExperimentCreator(
                probeRepository = probeRepository,
                experimentLengthRepository = experimentLengthRepository,
            )

            @Suppress("UNCHECKED_CAST")
            return ExperimentViewModel(
                experimentCreator = experimentCreator,
                experimentLogRepository = experimentLogRepository,
            ) as T

        } else {
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }

}
