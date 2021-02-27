package app.upaya.timer.session


class SessionRepositoryFake(private var sessionLength: Double = 10.0) : ISessionRepository {

    override fun loadSessionLength(): Double {
        return sessionLength
    }

    override fun storeSessionLength(sessionLength: Double) {
        this.sessionLength = sessionLength
    }

}
