package app.upaya.timer.timer

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent


class TimerAnalyticsLogger(activity: AppCompatActivity) {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(activity)
    private val timerViewModel = ViewModelProvider(activity, TimerViewModelFactory(activity.application)).get(TimerViewModel::class.java)

    fun logSessionFinished() {
        firebaseAnalytics.logEvent("timer_session") {
            timerViewModel.sessionLength.value?.let { this.param("duration", it) }
        }
    }

}
