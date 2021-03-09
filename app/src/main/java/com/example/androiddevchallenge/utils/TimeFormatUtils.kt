package com.example.androiddevchallenge.utils

/**
 * @author lipeilin
 * @date 2021/3/9
 */
object TimeFormatUtils {

    fun formatTime(time: Long): String {
        var value = time
        val seconds = value % 60
        value /= 60
        val minutes = value % 60
        value /= 60
        val hours = value % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun formatTimeMinute(time: Long): String {
        var value = time
        value /= 60
        val minutes = value % 60
        return String.format("%02d", minutes)
    }

    fun formatTimeSecond(time: Long): String {
        val value = time
        val seconds = value % 60
        return String.format("%02d", seconds)
    }
}
