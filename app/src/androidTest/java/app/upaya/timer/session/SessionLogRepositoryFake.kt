package app.upaya.timer.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlin.collections.ArrayList


class SessionLogRepositoryFake : ISessionLogRepository {

    private val _sessions = MutableLiveData<MutableList<SessionLog>>(ArrayList())
    val sessions: LiveData<List<SessionLog>> = Transformations.map(_sessions) { ArrayList(it) }

    override val lastSession: LiveData<SessionLog> = Transformations.map(_sessions) { it.last() }

    override suspend fun storeSession(sessionLog: SessionLog) {
        _sessions.value?.add(sessionLog)
        _sessions.value = _sessions.value  // inform about value change
    }

}
