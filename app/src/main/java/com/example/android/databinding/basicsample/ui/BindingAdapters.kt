package com.example.android.databinding.basicsample.ui

import android.view.View
import androidx.databinding.BindingAdapter


object BindingAdapters {

    @BindingAdapter("visibleWhen")
    @JvmStatic fun visibleWhen(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

}
