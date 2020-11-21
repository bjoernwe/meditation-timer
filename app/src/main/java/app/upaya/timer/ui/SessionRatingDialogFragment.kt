package app.upaya.timer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.R
import app.upaya.timer.timer.TimerAnalyticsLogger
import app.upaya.timer.timer.TimerViewModel
import app.upaya.timer.timer.TimerViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SessionRatingDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.session_rating_bottom_sheet, container, false)

        // User activity context for the same ViewModel as in the main activity
        val timerViewModelFactory = TimerViewModelFactory(requireActivity().applicationContext)
        val timerViewModel = ViewModelProvider(requireActivity(), timerViewModelFactory).get(TimerViewModel::class.java)
        val timerAnalyticsLogger = TimerAnalyticsLogger(requireActivity(), timerViewModel)

        view.findViewById<ImageView>(R.id.ratingDownImageView).setOnClickListener {
            timerViewModel.submitRating(1F)
            timerAnalyticsLogger.logSessionRating(1F)
            this.dismiss()
        }

        view.findViewById<ImageView>(R.id.ratingUpImageView).setOnClickListener {
            timerViewModel.submitRating(0F)
            timerAnalyticsLogger.logSessionRating(0F)
            this.dismiss()
        }

        return view
    }

}
