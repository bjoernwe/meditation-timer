package app.upaya.timer.session

import org.junit.Test


class SessionHandlerTest {

    @Test
    fun getSessionLength() {

        // GIVEN a timer
        val initialSessionLength = 10.0
        val timer = SessionHandler(initialSessionLength)

        // WHEN its session length is requested
        // THEN it is the same as the initial one
        println(timer.getLength())
        assert(timer.getLength() == initialSessionLength)

    }

    @Test
    fun increaseSessionLength() {

        // GIVEN a timer
        val initialSessionLength = 10.0
        val timer = SessionHandler(initialSessionLength)

        // WHEN its session length is increased
        timer.increaseLength()

        // THEN it is longer than before
        assert(timer.getLength() > initialSessionLength)

    }

    @Test
    fun decreaseSessionLength() {

        // GIVEN a timer
        val initialSessionLength = 10.0
        val timer = SessionHandler(initialSessionLength)

        // WHEN its session length is decreased
        timer.decreaseLength()

        // THEN it is longer than before
        assert(timer.getLength() < initialSessionLength)

    }

    @Test
    fun startCountDown() {

        // GIVEN a timer
        val callbacks = mutableListOf<String>()
        val initialSessionLength = 2.0
        val timer = SessionHandler(initialSessionLength)

        // WHEN the timer is started and run until the end
        repeat(2) {  // (second call should be ignored)
            timer.start(
                    onTick = { secondsRemaining -> callbacks.add("tick $secondsRemaining") },
                    onFinish = { callbacks.add("finish") }
            )
        }
        while (timer.isRunning()) Thread.sleep(100)

        // THEN all callbacks have been called
        assert(callbacks.size == 4)
        assert(callbacks[0] == "tick 2")
        assert(callbacks[1] == "tick 1")
        assert(callbacks[2] == "tick 0")
        assert(callbacks[3] == "finish")
    }

}
