package com.qusion.lib_roundprogressbar

import android.view.animation.Animation
import android.view.animation.Transformation

class RoundProgressBarAnimation(
    private val roundProgressBar: RoundProgressBar,
    private val newProgress: Int
) : Animation() {

    private val oldProgress = roundProgressBar.progress

    override fun applyTransformation(
        interpolatedTime: Float,
        transformation: Transformation?
    ) {
        val progress = if (oldProgress < newProgress) {
            oldProgress + ((newProgress - oldProgress) * interpolatedTime)
        } else {
            oldProgress - ((oldProgress - newProgress) * interpolatedTime)
        }
        roundProgressBar.animationProgress = progress
        roundProgressBar.requestLayout()
    }


}