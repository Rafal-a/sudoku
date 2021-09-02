package com.thechance.sudoku


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class SudokuBoard (context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {

    //extract those attributes and save them in individual variables to work with them individually
    private  val squareSize = 3
    private  val size = 9
    private var cellSizePixel = 0f
    private var selectedRow = 0
    private var selectedColumn = 0
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
    private  var selectedCellPaint = Paint().apply {
        style =Paint.Style.FILL_AND_STROKE
        strokeWidth= 2F
        color = Color.parseColor("#bbdefb")
    }
    private  var conflictedCellPaint = Paint().apply {
        style =Paint.Style.FILL_AND_STROKE
        strokeWidth= 2F
        color = Color.parseColor("#e2ebf3")
    }
    lateinit var  solver :Solver

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
        fillCells(canvas)
        drawLines(canvas)
    }
    //draw in the cell
    private fun fillCells(canvas: Canvas) {
        //nothing was selected
        if(selectedRow == -1  || selectedColumn == -1 )return
        for( row in 0 .. size ){
            for(column in 0 .. size){
                if(row == selectedRow && column == selectedColumn){
                    fillCell(canvas , row , column , selectedCellPaint)
                } else if(row == selectedRow || column == selectedColumn)
                    fillCell(canvas , row , column , conflictedCellPaint)
                //cell in the same group
                else if( row / squareSize == selectedRow/squareSize && column/squareSize ==selectedColumn/squareSize )
                    fillCell(canvas , row , column , conflictedCellPaint)

            }
        }

    }

    private fun fillCell(canvas: Canvas, row: Int, column: Int, paint: Paint) {
        canvas.drawRect(
            column * cellSizePixel,
            row * cellSizePixel,
            (column + 1) * cellSizePixel,
            (row + 1) * cellSizePixel,
            paint
        )
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
    //get the touch event from the user
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event!!.action){
            MotionEvent.ACTION_DOWN-> {handleTouchEvent(event.x , event.y , )
                true }
            else -> false
        }
    }
    private fun handleTouchEvent(x: Float, y: Float) {
        selectedRow = (y/cellSizePixel).toInt()
        selectedColumn = (x/cellSizePixel).toInt()
        // will redraw(refresh) the board
        invalidate()

    }


}