package app.upaya.timer.timer


class TimerRepositoryFake(private var sessionLength: Double = 10.0) : ITimerRepository {

    override fun loadSessionLength(): Double {
        return sessionLength
    }

    override fun storeSessionLength(sessionLength: Double) {
        this.sessionLength = sessionLength
    }

}
