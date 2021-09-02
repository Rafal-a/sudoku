package com.thechance.sudoku


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class SudokuBoard (context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {

    //extract those attributes and save them in individual variables to work with them individually
    private  val squareSize = 3
    private  val size = 9
    private var cellSizePixel = 0f

    private val thickLinePaint = Paint().apply {
        style =Paint.Style.STROKE
        strokeWidth= 4F
        color = Color.BLACK
    }
    private  val thinLinePaint=Paint().apply{
        style =Paint.Style.STROKE
        strokeWidth= 2F
        color = Color.BLACK
    }


    //get the height and width of the device
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixel = min ( widthMeasureSpec , heightMeasureSpec)
        setMeasuredDimension(sizePixel,sizePixel)
    }


    //draw the square
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        cellSizePixel = (width/size).toFloat()
        //draw the rect and the board
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), thickLinePaint)
        drawLines(canvas)
    }

      private fun drawLines(canvas: Canvas) {

          for (i in 1 until size) {
             val paintToUse = when(i % squareSize){
                 //whether we need to draw thick line or not
                 0 -> thickLinePaint
                 else -> thinLinePaint
                  }
              canvas.drawLine(
                  i * cellSizePixel,
                  0f,
                  i * cellSizePixel,
                  height.toFloat(),
                  paintToUse
              )
              canvas.drawLine(
                  0f ,
                  i * cellSizePixel,
                  width.toFloat(),
                  i * cellSizePixel,
                  paintToUse
              )
          }

      }


}