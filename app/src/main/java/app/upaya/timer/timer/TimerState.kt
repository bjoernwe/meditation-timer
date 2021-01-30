package app.upaya.timer.timer

import androidx.lifecycle.MutableLiveData


sealed class TimerState(
        /**
         This state machine models the timer's state transitions. It takes LiveData objects to post
         the current state and the number of remaining seconds to.
         */
        protected val timer: Timer,
        protected val timerState: MutableLiveData<TimerState?>,
        protected val secondsRemaining: MutableLiveData<Int>
)


class Idle(timer: Timer, timerState: MutableLiveData<TimerState?>, secondsRemaining: MutableLiveData<Int>)
    : TimerState(timer, timerState, secondsRemaining) {

    fun startCountdown() {
        val runningState = Running(timer, timerState, secondsRemaining)
        timerState.postValue(runningState)
        timer.startCountdown(
                onFinish = runningState::onFinish,
                onTick = runningState::onTick
        )
    }

}


class Running internal constructor(timer: Timer, liveData: MutableLiveData<TimerState?>, secondsRemaining: MutableLiveData<Int>)
    : TimerState(timer, liveData, secondsRemaining) {

    internal fun onFinish() {
        timerState.postValue(Finished(timer, timerState, secondsRemaining))
        secondsRemaining.postValue(0)
    }

    internal fun onTick(secondsRemaining: Int) {
        this.secondsRemaining.postValue(secondsRemaining)
    }

}


class Finished internal constructor(timer: Timer, liveData: MutableLiveData<TimerState?>, secondsRemaining: MutableLiveData<Int>)
    : TimerState(timer, liveData, secondsRemaining) {

    fun increaseSessionLength() {
        timer.increaseSessionLength()
        timerState.postValue(Idle(timer, timerState, secondsRemaining))
    }

    fun decreaseSessionLength() {
        timer.decreaseSessionLength()
        timerState.postValue(Idle(timer, timerState, secondsRemaining))
    }

}
