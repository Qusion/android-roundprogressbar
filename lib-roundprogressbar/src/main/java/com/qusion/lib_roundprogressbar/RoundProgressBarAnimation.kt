package com.qusion.lib_roundprogressbar

import android.view.animation.Animation
import android.view.animation.Transformation

class RoundProgressBarAnimation(
    private val roundProgressBar: RoundProgressBar,
    private val newProgress: Int,
    private val setAnimationProgress: (Float) -> Unit
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
        setAnimationProgress(progress)
        roundProgressBar.requestLayout()
    }


}