package com.mosz.sportapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : ComponentActivity() {
    private val testViewModel by viewModels<TestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testViewModel.init()
    }
}