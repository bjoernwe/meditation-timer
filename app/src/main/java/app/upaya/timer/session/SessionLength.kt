package app.upaya.timer.session


class SessionLength(length: Double) {

    var value = length
        private set

    fun increase() {
        value = value.times(1.1)
    }

    fun decrease() {
        val newSessionLength = value.times(.8)
        if (newSessionLength >= 1.0) {
            value = newSessionLength
        }
    }

}
