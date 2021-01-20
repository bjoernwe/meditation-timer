package app.upaya.timer.timer

import org.junit.Test


class TimerTest {

    @Test
    fun getSessionLength() {

        // GIVEN a timer
        val initialSessionLength = 10.0
        val timer = Timer(initialSessionLength)

        // WHEN its session length is requested
        // THEN it is the same as the initial one
        println(timer.getSessionLength())
        assert(timer.getSessionLength() == initialSessionLength)

    }

    @Test
    fun increaseSessionLength() {

        // GIVEN a timer
        val initialSessionLength = 10.0
        val timer = Timer(initialSessionLength)

        // WHEN its session length is increased
        timer.increaseSessionLength()

        // THEN it is longer than before
        assert(timer.getSessionLength() > initialSessionLength)

    }

    @Test
    fun decreaseSessionLength() {

        // GIVEN a timer
        val initialSessionLength = 10.0
        val timer = Timer(initialSessionLength)

        // WHEN its session length is decreased
        timer.decreaseSessionLength()

        // THEN it is longer than before
        assert(timer.getSessionLength() < initialSessionLength)

    }

    @Test
    fun startCountDown() {

        // GIVEN a timer
        val callbacks = mutableListOf<String>()
        val initialSessionLength = 2.0
        val timer = Timer(
                sessionLength = initialSessionLength,
                onStart = { callbacks.add("start") },
                onTick = { secondsRemaining -> callbacks.add("tick $secondsRemaining") },
                onFinish = { callbacks.add("finish") }
        )

        // WHEN the timer is started and run until the end
        timer.startCountdown()
        timer.startCountdown()  // should be ignored
        while (timer.isRunning()) Thread.sleep(100)

        // THEN all callbacks have been called
        assert(callbacks.size == 5)
        assert(callbacks[0] == "start")
        assert(callbacks[1] == "tick 2")
        assert(callbacks[2] == "tick 1")
        assert(callbacks[3] == "tick 0")
        assert(callbacks[4] == "finish")
    }

}
