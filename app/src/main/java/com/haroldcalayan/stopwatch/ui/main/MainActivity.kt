package com.haroldcalayan.stopwatch.ui.main

import androidx.activity.viewModels
import com.haroldcalayan.stopwatch.R
import com.haroldcalayan.stopwatch.base.BaseActivity
import com.haroldcalayan.stopwatch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

}