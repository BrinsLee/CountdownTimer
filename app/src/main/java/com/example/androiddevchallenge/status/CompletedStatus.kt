package com.example.androiddevchallenge.status

import com.example.androiddevchallenge.model.TimerViewModel

/**
 * @author lipeilin
 * @date 2021/3/9
 */
class CompletedStatus(private val viewModel: TimerViewModel) : IStatus {
    override fun getDisplayName(): String {
        return "Start"
    }

    override fun startButtonClick() = viewModel.animatorController.start()

    override fun cancelButtonClick() {
    }

    override fun sweepAngle(): Float = 0f
}