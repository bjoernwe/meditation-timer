package app.upaya.timer.timer


class TimerRepositoryFake : ITimerRepository {

    private var sessionLength = 10.0

    override fun loadSessionLength(): Double {
        return sessionLength
    }

    override fun storeSessionLength(sessionLength: Double) {
        this.sessionLength = sessionLength
    }

}
