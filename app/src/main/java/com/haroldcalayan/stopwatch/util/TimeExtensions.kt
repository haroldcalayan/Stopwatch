package com.haroldcalayan.stopwatch.util

fun Int.twoDigits(): String {
    if (this < 10) {
        return "0$this"
    }
    return "$this"
}