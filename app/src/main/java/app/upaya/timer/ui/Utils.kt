package app.upaya.timer.ui


fun fromSecsToTimeString(seconds: Int) : String {
    return "%d:%02d:%02d".format(seconds/3600, seconds/60, seconds%60)
}
