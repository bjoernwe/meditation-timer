package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations


class SessionRepositoryFake {

    private val _sessions = MutableLiveData<MutableList<Session>>(ArrayList())

    val sessionCount: LiveData<Int> = Transformations.map(_sessions) { it.size }
    val sessionAvg = Transformations.map(_sessions) { it.map { s -> s.length }.average().toFloat() }

    suspend fun storeSession(session: Session) {
        _sessions.value!!.add(session)
    }

}
