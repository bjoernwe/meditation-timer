package app.upaya.timer.timer

import android.os.CountDownTimer


class Timer(
        private var sessionLength: Double,
        private val onStart: () -> Unit = {},
        private val onTick: (secondsRemaining: Double) -> Unit = {},
        private val onFinish: (sessionLength: Double) -> Unit = {},
) {

    @Volatile
    private var countDownTimer: CountDownTimer? = null

    fun startCountdown() {

        synchronized(this) {

            if (countDownTimer != null) return

            val timerDuration: Long = sessionLength.toLong() * 1000

            countDownTimer = object : CountDownTimer(timerDuration, 1000) {

                override fun onTick(millisRemaining: Long) {
                    onTick(millisRemaining / 1000.0)
                }

                override fun onFinish() {
                    countDownTimer = null
                    this@Timer.onFinish(sessionLength)
                }

            }.start()

            onStart()

        }

    }

    fun getSessionLength(): Double {
        return sessionLength
    }

    fun increaseSessionLength() {
        sessionLength = sessionLength.times(1.1)
    }

    fun decreaseSessionLength() {
        val newSessionLength = sessionLength.times(0.8)
        if (newSessionLength >= 1.0) {
            sessionLength = newSessionLength
        }
    }

}
