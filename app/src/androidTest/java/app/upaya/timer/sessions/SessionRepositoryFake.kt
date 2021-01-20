package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class SessionRepositoryFake : ISessionRepository {

    private val _sessions = MutableLiveData<MutableList<Session>>(ArrayList())

    private val _sessionCount = MutableLiveData(0)
    override val sessionCount: LiveData<Int> = _sessionCount

    private val _sessionAvg = MutableLiveData(0f)
    override val sessionAvg: LiveData<Float> = _sessionAvg

    init {
        _sessions.observeForever { _sessionCount.value = it.size }
        _sessions.observeForever { _sessionAvg.value = it.map { s -> s.length }.average().toFloat() }
    }

    override suspend fun storeSession(session: Session) {
        _sessions.value?.add(session)
    }

}
