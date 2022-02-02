package com.haroldcalayan.stopwatch.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val showProgressBar = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        throwable.printStackTrace()
        showProgressBar.postValue(false)
    }

    private val exceptionHandlerForBackgroundTask = CoroutineExceptionHandler { _, throwable: Throwable ->
        throwable.printStackTrace()
    }

    fun invoke(task: suspend () -> Unit) {
        showProgressBar.postValue(true)
        viewModelScope.launch(exceptionHandler) {
            task.invoke()
            showProgressBar.postValue(false)
        }
    }

    fun invokeAtBackground(task: suspend () -> Unit) {
        viewModelScope.launch(exceptionHandlerForBackgroundTask) { task.invoke() }
    }

    fun invokeUnsafely(task: suspend () -> Unit) {
        showProgressBar.postValue(true)
        viewModelScope.launch {
            task.invoke()
            showProgressBar.postValue(false)
        }
    }

}