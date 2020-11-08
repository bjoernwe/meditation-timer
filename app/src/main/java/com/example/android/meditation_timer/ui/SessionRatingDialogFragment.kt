package com.example.android.meditation_timer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.android.meditation_timer.R
import com.example.android.meditation_timer.timer.TimerViewModel
import com.example.android.meditation_timer.timer.TimerViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SessionRatingDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.session_rating_bottom_sheet, container, false)

        // User activity context for the same ViewModel as in the main activity
        val timerViewModelFactory = TimerViewModelFactory(activity!!.applicationContext)
        val timerViewModel = ViewModelProvider(activity!!, timerViewModelFactory).get(TimerViewModel::class.java)

        view.findViewById<ImageView>(R.id.ratingDownImageView).setOnClickListener {
            timerViewModel.submitRating(1F, 1)
            this.dismiss()
        }

        view.findViewById<ImageView>(R.id.ratingUpImageView).setOnClickListener {
            timerViewModel.submitRating(0F, 1)
            this.dismiss()
        }

        return view
    }

}
