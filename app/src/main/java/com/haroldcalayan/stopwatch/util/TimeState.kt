package com.haroldcalayan.stopwatch.util

import androidx.annotation.IntDef

const val INITIAL = 0
const val STARTED = 1
const val STOPPED = 2

@Retention(AnnotationRetention.SOURCE)
@IntDef(INITIAL, STARTED, STOPPED)
annotation class TimeState