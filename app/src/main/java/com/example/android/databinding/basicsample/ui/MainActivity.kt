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

package com.example.android.databinding.basicsample.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.data.TimerStates
import com.example.android.databinding.basicsample.data.TimerViewModel
import com.example.android.databinding.basicsample.databinding.MainActivityBinding


class MainActivity : AppCompatActivity(), Observer<TimerStates>, RatingBar.OnRatingBarChangeListener {

    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Obtain ViewModel from ViewModelProviders
        val timerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)

        // Obtain binding
        val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewmodel = timerViewModel
        binding.lifecycleOwner = this

        // Register event callbacks
        timerViewModel.state.observe(this, this)
        binding.ratingBar.onRatingBarChangeListener = this

    }

    override fun onStart() {
        super.onStart()
        mediaPlayer = MediaPlayer.create(this, R.raw.bell_347378)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onChanged(newTimerState: TimerStates?) {
        when (newTimerState) {
            TimerStates.FINISHED -> { playBell() }
            else -> {}
        }
    }

    private fun playBell() {
        mediaPlayer?.start()
    }

    override fun onRatingChanged(ratingBar: RatingBar, rating: Float, fromUser: Boolean) {
        if (fromUser) {
            val timerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
            timerViewModel.submitRating(rating, ratingBar.max)
            ratingBar.rating = 0F
        }
    }

}
