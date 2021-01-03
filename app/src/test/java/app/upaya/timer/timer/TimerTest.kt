package app.upaya.timer.timer

import org.junit.Test


class TimerTest {

    @Test
    fun startCountdown() {
    }

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

}
