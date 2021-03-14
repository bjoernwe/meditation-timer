package app.upaya.timer.hints

import java.util.*


data class Hint(
    val hint: String,
    val id: UUID = UUID.nameUUIDFromBytes(hint.toByteArray()),
)
