package app.upaya.timer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.R
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

        // Use application context for the same ViewModel as in the main activity
        val timerViewModelFactory = TimerViewModelFactory(requireActivity().application)
        val timerViewModel = ViewModelProvider(requireActivity(), timerViewModelFactory).get(TimerViewModel::class.java)

        view.findViewById<ImageView>(R.id.ratingDownImageView).setOnClickListener {
            timerViewModel.increaseSessionLength()
            this.dismiss()
        }

        view.findViewById<ImageView>(R.id.ratingUpImageView).setOnClickListener {
            timerViewModel.decreaseSessionLength()
            this.dismiss()
        }

        return view
    }

}
