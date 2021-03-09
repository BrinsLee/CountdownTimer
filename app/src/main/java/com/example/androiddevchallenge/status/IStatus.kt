package com.example.androiddevchallenge.status

/**
 * @author lipeilin
 * @date 2021/3/9
 */
interface IStatus {

    fun getDisplayName() : String

    fun startButtonClick()

    fun cancelButtonClick()

    fun sweepAngle(): Float
}