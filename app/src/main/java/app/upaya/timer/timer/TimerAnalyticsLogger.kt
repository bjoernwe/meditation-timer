package app.upaya.timer.timer

import android.app.Activity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

class TimerAnalyticsLogger(activity: Activity, private val timerViewModel: TimerViewModel?) {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(activity)

    fun logSessionFinished() {
        firebaseAnalytics.logEvent("timer_session") {
            timerViewModel?.sessionLength?.value?.let { this.param("duration", it) }
        }
    }

    fun logSessionRating(rating: Float) {
        firebaseAnalytics.logEvent("timer_session_rating") {
            this.param("rating", rating.toDouble())
        }
    }

}
