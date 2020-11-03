/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.databinding.basicsample.data

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.*


/**
 * This class is used as a variable in the XML layout and it's fully observable, meaning that
 * changes to any of the exposed observables automatically refresh the UI. *
 */
class TimerViewModel : ViewModel() {

    private val _secondsLeft =  MutableLiveData(0)
    private val _gettingRating =  MutableLiveData(false)

    val secondsLeft: LiveData<Int> = _secondsLeft
    val gettingRating: LiveData<Boolean> = _gettingRating

    fun startCountdown() {

        _secondsLeft.value = 10
        _gettingRating.value = false

        val timerDuration: Long = (_secondsLeft.value ?: 0).toLong() * 1000

        object: CountDownTimer(timerDuration, 1000) {

            override fun onTick(millisRemaining: Long) {
                _secondsLeft.value = round(millisRemaining / 1000.0).toInt()
            }

            override fun onFinish() {
                _secondsLeft.value = 0
                _gettingRating.value = true
            }

        }.start()

    }

}
