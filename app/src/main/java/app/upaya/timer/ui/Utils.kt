package app.upaya.timer.ui


fun fromSecsToTimeString(seconds: Int?) : String {
    val secs = seconds ?: 0
    return "%d:%02d:%02d".format(secs/3600, (secs/60)%60, secs%60)
}
