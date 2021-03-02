package app.upaya.timer.session

import org.junit.Test


class SessionTimerTest {

    @Test
    fun increaseSessionLength() {

        // GIVEN a timer
        val sessionTimerRepositoryFake = SessionLengthRepositoryFake()
        val initialSessionLength = sessionTimerRepositoryFake.loadSessionLength()
        val timer = SessionTimer(sessionTimerRepositoryFake)

        // WHEN its session length is increased
        timer.increaseLength()

        // THEN it is longer than before
        assert(timer.getLength() > initialSessionLength)

    }

    @Test
    fun decreaseSessionLength() {

        // GIVEN a timer
        val sessionTimerRepositoryFake = SessionLengthRepositoryFake()
        val initialSessionLength = sessionTimerRepositoryFake.loadSessionLength()
        val timer = SessionTimer(sessionTimerRepositoryFake)

        // WHEN its session length is decreased
        timer.decreaseLength()

        // THEN it is longer than before
        assert(timer.getLength() < initialSessionLength)

    }

    @Test
    fun startCountDown() {

        // GIVEN a timer
        val finished = false
        val sessionTimerRepositoryFake = SessionLengthRepositoryFake()
        sessionTimerRepositoryFake.storeSessionLength(2.0)
        val timer = SessionTimer(sessionTimerRepositoryFake)

        // WHEN the timer is started and run until the end
        repeat(2) {  // (second call should be ignored)
            timer.start(onFinish = { finished = true })
        }
        while (timer.isRunning()) Thread.sleep(100)

        // THEN all callbacks have been called
        assert(finished)
    }

}
