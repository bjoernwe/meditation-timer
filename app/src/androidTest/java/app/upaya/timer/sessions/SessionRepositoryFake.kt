package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations


class SessionRepositoryFake : ISessionRepository {

    private val _sessions = MutableLiveData<MutableList<Session>>(ArrayList())

    override val sessionCount: LiveData<Int> = Transformations.map(_sessions) { it.size }
    override val sessionAvg = Transformations.map(_sessions) {
        val avg = it.map { s -> s.length }.average().toFloat()
        if (avg.isNaN()) return@map 0f else return@map avg
    }

    override suspend fun storeSession(session: Session) {
        _sessions.value!!.add(session)
        _sessions.value = _sessions.value  // notify LiveData about change
    }

}
