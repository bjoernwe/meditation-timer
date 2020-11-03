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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.data.TimerModel
import com.example.android.databinding.basicsample.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain ViewModel from ViewModelProviders
        val viewModel = ViewModelProviders.of(this).get(TimerModel::class.java)

        // An alternative ViewModel using Observable fields and @Bindable properties can be used:
        // val viewModel = ViewModelProviders.of(this).get(ProfileObservableViewModel::class.java)

        // Obtain binding
        val binding: MainActivityBinding =
                DataBindingUtil.setContentView(this, R.layout.main_activity)

        // Bind layout with ViewModel
        binding.viewmodel = viewModel

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this
    }
}
