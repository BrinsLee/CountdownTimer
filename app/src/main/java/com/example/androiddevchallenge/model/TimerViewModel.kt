package com.example.androiddevchallenge.model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.controller.AnimatorController
import com.example.androiddevchallenge.status.*

/**
 * @author lipeilin
 * @date 2021/3/9
 */
const val MAX_INPUT_VALUE = 60
const val MIN_INPUT_VALUE = 0

class TimerViewModel : ViewModel() {
    var counting: Boolean by mutableStateOf(false)

    var totalTime: Long by mutableStateOf(0)

    var timeLeft: Long by mutableStateOf(0)

    var timeMinute: Int by mutableStateOf(0)

    var timeSecond: Int by mutableStateOf(0)

    var status: IStatus by mutableStateOf(InitStatus(this))

    var animValue: Float by mutableStateOf(0.0f)

    var animatorController = AnimatorController(this)

    fun updateMinute(minute: Int) {
        timeMinute = minute
        updateTotalTime()
    }

    fun updateSecond(second: Int) {
        if (second == 60) {
            timeMinute += 1
            timeSecond = 0
        } else {
            timeSecond = second
        }
        updateTotalTime()
    }

    private fun updateTotalTime() {
        totalTime = (timeMinute * MAX_INPUT_VALUE + timeSecond).toLong()
        timeLeft = totalTime
    }

    fun start() {
        counting = true
        status = StartedStatus(this)
    }

    fun pause() {
        status = PausedStatus(this)
    }

    fun resume() {
        status = StartedStatus(this)
    }

    fun complete() {
        totalTime = 0
        timeLeft = 0
        timeMinute = 0
        timeSecond = 0
        counting = false
        status = CompletedStatus(this)
    }
}