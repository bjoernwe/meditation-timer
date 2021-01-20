package app.upaya.timer.timer

import kotlin.concurrent.thread
import kotlin.math.roundToInt


class Timer(
        private var sessionLength: Double,
        private val onStart: () -> Unit = {},
        private val onTick: (secondsRemaining: Int) -> Unit = {},
        private val onFinish: () -> Unit = {},
        private val onSessionLengthChanged: (newSessionLength: Double) -> Unit = {}
) {

    @Volatile
    private var countDownThread: Thread? = null

    fun startCountdown() {

        synchronized(this) {

            if (countDownThread != null) return

            countDownThread = thread(start = true, isDaemon = true) {

                val sessionLength = this.sessionLength.roundToInt()

                onStart()
                onTick(sessionLength)

                for (i in 1..sessionLength) {
                    Thread.sleep(1000)
                    val secondsRemaining = sessionLength - i
                    onTick(secondsRemaining)
                }

                onFinish()
                countDownThread = null

            }  // thread

        }  // synchronized

    }  // startCountdown()

    fun getSessionLength(): Double {
        return sessionLength
    }

    fun increaseSessionLength() {
        sessionLength = sessionLength.times(1.1)
        onSessionLengthChanged(sessionLength)
    }

    fun decreaseSessionLength() {
        val newSessionLength = sessionLength.times(0.8)
        if (newSessionLength >= 1.0) {
            sessionLength = newSessionLength
            onSessionLengthChanged(sessionLength)
        }
    }

    fun isRunning(): Boolean {
        return countDownThread != null
    }

}
