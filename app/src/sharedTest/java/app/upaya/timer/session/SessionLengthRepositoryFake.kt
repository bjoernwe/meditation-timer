package app.upaya.timer.session

import app.upaya.timer.settings.ISessionLengthRepository


class SessionLengthRepositoryFake(
    private var sessionLength: Double = 10.0
) : ISessionLengthRepository {

    override fun loadSessionLength(): Double {
        return sessionLength
    }

    override fun storeSessionLength(sessionLength: Double) {
        this.sessionLength = sessionLength
    }

}
