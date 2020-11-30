package com.ygq.edittextdemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.annotation.RestrictTo
import androidx.constraintlayout.widget.ConstraintLayout
import com.ygq.edittextdemo.R
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * 圆角的ConstraintLayout
 * Author：yeguoqiang
 * Created time：2020/3/19 12:30
 */
class RoundCornerConstraintLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val mPath: Path
    private var mRadius = 0f
    private var mLastRadius = 0f
    private var mWidth = 0
    private var mHeight = 0
    private var mRoundMode = MODE_ALL

    /**
     * The direction of the corner.
     */
    @IntDef(
            MODE_NONE,
            MODE_ALL,
            MODE_LEFT,
            MODE_TOP,
            MODE_RIGHT,
            MODE_BOTTOM
    )
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    annotation class RoundMode

    /**
     * 设置是否圆角裁边
     *
     * @param roundMode
     */
    fun setRoundMode(@RoundMode roundMode: Int) {
        mRoundMode = roundMode
    }

    /**
     * 设置圆角半径
     *
     * @param radius
     */
    fun setCornerRadius(radius: Float) {
        mRadius = radius
    }

    private fun checkPathChanged() {
        if (width == mWidth && height == mHeight && mLastRadius == mRadius
        ) {
            return
        }
        mWidth = width
        mHeight = height
        mLastRadius = mRadius
        mPath.reset()
        when (mRoundMode) {
            MODE_ALL -> mPath.addRoundRect(
                    RectF(
                            0f,
                            0f,
                            mWidth.toFloat(),
                            mHeight.toFloat()
                    ), mRadius, mRadius, Path.Direction.CW
            )
            MODE_LEFT -> mPath.addRoundRect(
                    RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                    floatArrayOf(mRadius, mRadius, 0f, 0f, 0f, 0f, mRadius, mRadius),
                    Path.Direction.CW
            )
            MODE_TOP -> mPath.addRoundRect(
                    RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                    floatArrayOf(mRadius, mRadius, mRadius, mRadius, 0f, 0f, 0f, 0f),
                    Path.Direction.CW
            )
            MODE_RIGHT -> mPath.addRoundRect(
                    RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                    floatArrayOf(0f, 0f, mRadius, mRadius, mRadius, mRadius, 0f, 0f),
                    Path.Direction.CW
            )
            MODE_BOTTOM -> mPath.addRoundRect(
                    RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                    floatArrayOf(0f, 0f, 0f, 0f, mRadius, mRadius, mRadius, mRadius),
                    Path.Direction.CW
            )
        }
    }

    override fun draw(canvas: Canvas) {
        if (mRoundMode != MODE_NONE) {
            val saveCount = canvas.save()
            checkPathChanged()
            canvas.clipPath(mPath)
            super.draw(canvas)
            canvas.restoreToCount(saveCount)
        } else {
            super.draw(canvas)
        }
    }

    companion object {
        const val MODE_NONE = 0
        const val MODE_ALL = 1
        const val MODE_LEFT = 2
        const val MODE_TOP = 3
        const val MODE_RIGHT = 4
        const val MODE_BOTTOM = 5
        private const val DEFAULT_RADIUS = 0f
        private const val DEFAULT_BG_COLOR = Color.WHITE
    }

    init {
        val a = context.theme
                .obtainStyledAttributes(attrs, R.styleable.RoundCornerConstraintLayout, defStyleAttr, 0)
        val rClBgColor = a.getColor(
                R.styleable.RoundCornerConstraintLayout_rcClBgColor,
                DEFAULT_BG_COLOR
        )
        val rClRadius = a.getDimension(
                R.styleable.RoundCornerConstraintLayout_rcClRadius,
                DEFAULT_RADIUS
        )
        mRoundMode = a.getInt(
                R.styleable.RoundCornerConstraintLayout_rcClRoundMode,
                MODE_ALL
        )
        a.recycle()
        background = ColorDrawable(rClBgColor)
        mPath = Path()
        mPath.fillType = Path.FillType.EVEN_ODD
        setCornerRadius(rClRadius)
    }
}