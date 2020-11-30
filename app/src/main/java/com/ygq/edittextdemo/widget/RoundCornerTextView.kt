package com.ygq.edittextdemo.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.ygq.edittextdemo.R

/**
 * 支持圆角的TextView
 * Created time：2020/3/18 21:42
 */
class RoundCornerTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setBackgroungColor(@ColorInt color: Int) {
        val myGrad = background as GradientDrawable
        myGrad.setColor(color)
    }

    init {
        val a = context.theme
            .obtainStyledAttributes(attrs, R.styleable.RoundCornerTextView, defStyleAttr, 0)
        val rvTvBorderWidth =
            a.getDimensionPixelSize(R.styleable.RoundCornerTextView_rcTvBorderWidth, 0)
        val rvTvBorderColor = a.getColor(
            R.styleable.RoundCornerTextView_rcTvBorderColor,
            Color.BLACK
        )
        val rvTvRadius =
            a.getDimension(R.styleable.RoundCornerTextView_rcTvRadius, 0f)
        val rvTvBgColor = a.getColor(
            R.styleable.RoundCornerTextView_rcTvBgColor,
            Color.WHITE
        )
        a.recycle()
        val gd = GradientDrawable()
        gd.setColor(rvTvBgColor)
        gd.cornerRadius = rvTvRadius
        if (rvTvBorderWidth > 0) {
            gd.setStroke(rvTvBorderWidth, rvTvBorderColor)
        }
        this.background = gd
    }
}