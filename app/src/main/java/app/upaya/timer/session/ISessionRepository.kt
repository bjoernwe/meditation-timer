package app.upaya.timer.session

import androidx.lifecycle.LiveData
import java.util.*


interface ISessionRepository {
    val sessions: LiveData<List<SessionDetails>>
    suspend fun storeSession(length: Double, endDate: Date = Date())
}
