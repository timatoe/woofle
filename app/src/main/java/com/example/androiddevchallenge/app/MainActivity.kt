/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.example.androiddevchallenge.ui.WoofleApp
import com.example.androiddevchallenge.ui.dogdetail.DogDetailViewModel
import com.example.androiddevchallenge.ui.dogs.DogsViewModel

class MainActivity : ComponentActivity() {

    private val dogsViewModel: DogsViewModel by viewModels { getViewModelFactory() }
    private val dogDetailViewModel: DogDetailViewModel by viewModels { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WoofleApp(
                dogsViewModel,
                dogDetailViewModel,
                onBackPressedDispatcher
            )
        }
    }
}

fun ComponentActivity.getViewModelFactory(): ViewModelFactory {
    val repository = App().dogsRepository
    return ViewModelFactory(repository, this)
}