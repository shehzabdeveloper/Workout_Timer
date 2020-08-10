package com.shehzabahammad.workout_timer.util

object Util {

    fun appendZero(time: Long): String {
        val timeString = time.toString()
        return if (time < 10) "0$timeString" else timeString
    }
}