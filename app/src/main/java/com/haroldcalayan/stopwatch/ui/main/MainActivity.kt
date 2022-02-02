package com.haroldcalayan.stopwatch.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.haroldcalayan.stopwatch.R
import com.haroldcalayan.stopwatch.base.BaseActivity
import com.haroldcalayan.stopwatch.databinding.ActivityMainBinding
import com.haroldcalayan.stopwatch.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    private val activityScope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.buttonStartStop.text = getString(R.string.start)
        binding.buttonStartStop.setOnClickListener {
            viewModel.triggerStartOrStop()
        }
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    override fun onPause() {
        stopTimer()
        super.onPause()
    }

    override fun observe() {
        super.observe()
        viewModel.timeCounter.observe(this, {
            setTimeDisplay(it ?: 0)
        })

        viewModel.timerState.observe(this, {
            when (it) {
                INITIAL -> {
                    setTimeDisplay(0)
                    binding.buttonStartStop.text = getString(R.string.start)
                }
                STOPPED -> {
                    stopTimer()
                    binding.buttonStartStop.text = getString(R.string.start)
                }
                STARTED -> {
                    startTimer()
                    binding.buttonStartStop.text = getString(R.string.stop)
                }
            }
        })
    }

    private fun startTimer() {
        if (job == null) {
            job = activityScope.launch {
                while (true) {
                    if (viewModel.timerState.value == STARTED) {
                        delay(Constants.ONE_SECOND)
                        viewModel.incrementTime()
                    }
                }
            }
        }
    }

    private fun stopTimer() {
        job?.cancel()
        job = null
    }

    private fun setTimeDisplay(timeCounter: Int) {
        val secs = timeCounter % 60
        var mins = timeCounter / 60
        var hours = mins / 60
        if (mins > 60) mins %= 60
        if (hours > 60) hours %= 60
        binding.textviewTime.text = "${hours.twoDigits()}:${mins.twoDigits()}:${secs.twoDigits()}"
    }

}