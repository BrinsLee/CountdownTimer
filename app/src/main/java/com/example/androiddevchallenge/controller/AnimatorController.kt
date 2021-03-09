package com.example.androiddevchallenge.controller

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.example.androiddevchallenge.model.TimerViewModel

/**
 * @author lipeilin
 * @date 2021/3/9
 */
const val SPEED = 100

class AnimatorController(private val viewModel: TimerViewModel) {
    private var animator: ValueAnimator? = null

    fun start() {
        if (viewModel.totalTime == 0L) return
        if (animator == null) {
            animator = ValueAnimator.ofInt(viewModel.totalTime.toInt() * SPEED, 0)
            animator?.interpolator = LinearInterpolator()
            // Update timeLeft in ViewModel
            animator?.addUpdateListener {
                viewModel.animValue = (it.animatedValue as Int) / SPEED.toFloat()
                viewModel.timeLeft = (it.animatedValue as Int).toLong() / SPEED
            }
            animator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    complete()
                }
            })
        } else {
            animator?.setIntValues(viewModel.totalTime.toInt() * SPEED, 0)
        }
        animator?.duration = viewModel.totalTime * 1000L
        animator?.start()
        viewModel.start()
    }

    fun pause() {
        animator?.pause()
        viewModel.pause()
    }

    fun resume() {
        animator?.resume()
        viewModel.resume()
    }

    fun stop() {
        animator?.cancel()
        viewModel.complete()
    }

    fun complete() {
        viewModel.complete()
    }
}