package com.jchavez.wheelycoolapp.utils

import android.util.Log
import java.util.*

object CircleCalculator {
    fun getAngleSizeInDegrees(itemCount: Int): Float {
        return (360.0 / itemCount).toFloat()
    }

    fun generateRandomRotationValue(itemCount: Int): Float {
        val angleSize = getAngleSizeInDegrees(itemCount)
        val generatedValue = (angleSize * Random().nextInt(itemCount)) + 3600
        return generatedValue
    }
}