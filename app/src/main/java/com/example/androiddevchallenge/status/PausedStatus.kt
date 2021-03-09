package com.example.androiddevchallenge.status

import com.example.androiddevchallenge.model.TimerViewModel

/**
 * @author lipeilin
 * @date 2021/3/9
 */
class PausedStatus(private val viewModel: TimerViewModel): IStatus {
    override fun getDisplayName(): String {
        return "Resume"
    }

    override fun startButtonClick() = viewModel.animatorController.resume()

    override fun cancelButtonClick() = viewModel.animatorController.stop()

    override fun sweepAngle(): Float = viewModel.animValue / viewModel.totalTime * 360
}