package com.jchavez.wheelycoolapp.widgets

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import com.jchavez.wheelycoolapp.R
import com.jchavez.wheelycoolapp.models.WheelOption
import com.jchavez.wheelycoolapp.utils.CircleCalculator

class WheelView : View {
    private var currentDegrees: Float = 0f

    var items = arrayListOf<WheelOption>()

    private var colors = listOf(
            ContextCompat.getColor(context, R.color.red),
            ContextCompat.getColor(context, R.color.pink),
            ContextCompat.getColor(context, R.color.purple),
            ContextCompat.getColor(context, R.color.deepPurple),
            ContextCompat.getColor(context, R.color.indigo),
            ContextCompat.getColor(context, R.color.blue),
            ContextCompat.getColor(context, R.color.lightBlue),
            ContextCompat.getColor(context, R.color.cyan),
            ContextCompat.getColor(context, R.color.teal),
            ContextCompat.getColor(context, R.color.green))

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val attributes = context.obtainStyledAttributes(
                attrs, R.styleable.WheelView, defStyle, 0)
        attributes.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSections(canvas)
        drawItemLabels(canvas)
    }

    private fun drawSections(canvas: Canvas) {
        val angleSize = CircleCalculator.getAngleSizeInDegrees(items.count())
        for (i in items.indices) {
            val paint = Paint()
            paint.style = Paint.Style.FILL
            paint.color = colors[i]
            paint.isAntiAlias = true
            canvas.drawArc(
                    RectF(0f, 0f, width.toFloat(), width.toFloat()),
                    (angleSize * i),
                    angleSize,
                    true,
                    paint)
        }
    }

    private fun drawItemLabels(canvas: Canvas) {
        val angleSize = CircleCalculator.getAngleSizeInDegrees(items.count())
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.LTGRAY
        paint.textSize = resources.getDimensionPixelSize(R.dimen.wheel_text_size).toFloat()
        paint.textAlign = Paint.Align.CENTER
        paint.isAntiAlias = true
        canvas.rotate(angleSize / 2.0f, width / 2.0f, height / 2.0f)
        for (i in items.indices) {
            val bounds = Rect()
            paint.getTextBounds(items[i].name, 0, items[i].name.length, bounds)
            val paintHeight = bounds.height()
            canvas.rotate(angleSize, width /2.0f, height / 2.0f)
            canvas.drawText(items[i].name, (width / 2.0f) + (width / 4.0f), (height / 2.0f) + (paintHeight / 3.0f), paint)
        }
    }

    fun rotateWheel() {
        val randomValue = CircleCalculator.generateRandomRotationValue(items.count())
        val rotateAnimation = RotateAnimation(currentDegrees, (currentDegrees + randomValue + (CircleCalculator.getAngleSizeInDegrees(items.count()) / 2.0f)), 1, 0.5f, 1, 0.5f)
        currentDegrees = (currentDegrees + randomValue) % 360
        rotateAnimation.duration = randomValue.toLong()
        rotateAnimation.fillAfter = true
        rotateAnimation.interpolator = DecelerateInterpolator()
        this.animation = rotateAnimation
        startAnimation(rotateAnimation)

    }
}
