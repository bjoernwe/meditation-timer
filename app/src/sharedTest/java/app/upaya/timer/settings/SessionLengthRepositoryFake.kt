package app.upaya.timer.settings

import app.upaya.timer.experiments.repositories.length.ISessionLengthRepository


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
