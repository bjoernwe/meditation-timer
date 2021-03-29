package app.upaya.timer.probes

import java.util.*


data class Probe(
    val probe: String,
    val id: UUID = UUID.nameUUIDFromBytes(probe.toByteArray()),
)
