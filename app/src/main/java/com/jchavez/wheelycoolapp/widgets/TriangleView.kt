package com.jchavez.wheelycoolapp.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class TriangleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val path = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        canvas.drawPath(configurePath(width.toFloat(), path), configurePaint(paint))
    }

    private fun configurePaint(paint: Paint): Paint {
        paint.color = android.graphics.Color.BLACK
        paint.isAntiAlias = true
        return paint
    }

    private fun configurePath(sideLength: Float, path: Path): Path {
        path.moveTo(sideLength, 0f)
        path.lineTo(0f, (sideLength / 2f))
        path.lineTo(sideLength, sideLength)
        return path
    }
}