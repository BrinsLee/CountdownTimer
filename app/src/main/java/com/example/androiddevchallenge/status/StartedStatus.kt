package com.example.androiddevchallenge.status

import com.example.androiddevchallenge.model.TimerViewModel

/**
 * @author lipeilin
 * @date 2021/3/9
 */
class StartedStatus(private val viewModel: TimerViewModel) : IStatus {
    override fun getDisplayName(): String {
        return "Pause"
    }

    override fun startButtonClick() = viewModel.animatorController.pause()

    override fun cancelButtonClick() = viewModel.animatorController.stop()

    override fun sweepAngle() = viewModel.animValue / viewModel.totalTime * 360
}