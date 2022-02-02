package com.haroldcalayan.stopwatch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haroldcalayan.stopwatch.base.BaseViewModel
import com.haroldcalayan.stopwatch.util.INITIAL
import com.haroldcalayan.stopwatch.util.STARTED
import com.haroldcalayan.stopwatch.util.STOPPED

class MainViewModel : BaseViewModel() {

    private val _timeCounter = MutableLiveData<Int>()
    val timeCounter: LiveData<Int> = _timeCounter

    private val _timerState = MutableLiveData<Int>()
    val timerState: LiveData<Int> = _timerState

    init {
        _timerState.postValue(INITIAL)
    }

    fun incrementTime() {
        if (_timerState.value == STARTED) {
            _timeCounter.postValue((_timeCounter.value ?: 0).plus(1))
        }
    }

    fun reset() {
        _timeCounter.postValue(0)
    }

    fun triggerStartOrStop() {
        if (_timerState.value == INITIAL || _timerState.value == STOPPED) {
            _timerState.postValue(STARTED)
        } else {
            _timeCounter.postValue(0)
            _timerState.postValue(STOPPED)
        }
    }
}