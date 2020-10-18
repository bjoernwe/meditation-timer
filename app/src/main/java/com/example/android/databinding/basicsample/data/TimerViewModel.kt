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


/**
 * This class is used as a variable in the XML layout and it's fully observable, meaning that
 * changes to any of the exposed observables automatically refresh the UI. *
 */
class ProfileLiveDataViewModel : ViewModel() {

    private val _counter =  MutableLiveData(0)

    val counter: LiveData<Int> = _counter

    fun startCountdown() {

        _counter.value = 10

        val timerDuration: Long = 1000*(_counter.value ?: 0).toLong()

        val timer = object: CountDownTimer(timerDuration, 1000) {

            override fun onTick(millisRemaining: Long) {
                _counter.value = (millisRemaining / 1000).toInt()
            }

            override fun onFinish() {
                _counter.value = 0
            }

        }.start()

    }

}
