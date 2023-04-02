package kr.timoky.accountbook.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kr.timoky.accountbook.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object Common {
    fun calculationTime(createDateTime: Long): String {
        val calendar = Calendar.getInstance() //현재 시간
        val differenceValue = calendar.timeInMillis - createDateTime //현재 시간 - 비교가 될 시간
        val createDate = Date(createDateTime)
        val nowYear = calendar.get(Calendar.YEAR)
        calendar.time = createDate

        return when {
            /*differenceValue in 0..59999 -> { //59초 보다 적다면
                "방금 전"
            }

            differenceValue in 60000..3599999 -> { //59분 보다 적다면
                TimeUnit.MILLISECONDS.toMinutes(differenceValue).toString() + "분 전"
            }

            differenceValue in 3600000..86400000 -> { //23시간 보다 적다면
                TimeUnit.MILLISECONDS.toHours(differenceValue).toString() + "시간 전"
            }*/

            differenceValue in 0..86400000 -> {
                "오늘"
            }

            calendar.get(Calendar.YEAR) == nowYear -> { // 올해라면
                SimpleDateFormat("MM월 dd일", Locale.KOREA).format(createDate)
            }

            else -> { //그 외
                SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(createDate)
            }
        }
    }

    fun showSnackBar(
        activity: Activity,
        message: String
    ) {
        val view = activity.findViewById<CoordinatorLayout>(R.id.coordinator)
        val fab = activity.findViewById<FloatingActionButton>(R.id.fab)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAnchorView(fab)
            .show()
    }
}

object AnimationUtils {
    fun lerp(
        startValue: Float,
        endValue: Float,
        @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) fraction: Float
    ): Float {
        return startValue + fraction * (endValue - startValue)
    }

    /**
     * Linearly interpolate between two values
     */
    fun lerp(
        startValue: Int,
        endValue: Int,
        @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) fraction: Float
    ): Int {
        return (startValue + fraction * (endValue - startValue)).roundToInt()
    }

    /**
     * Linearly interpolate between two values when the fraction is in a given range.
     */
    fun lerp(
        startValue: Float,
        endValue: Float,
        @FloatRange(
            from = 0.0,
            fromInclusive = true,
            to = 1.0,
            toInclusive = false
        ) startFraction: Float,
        @FloatRange(from = 0.0, fromInclusive = false, to = 1.0, toInclusive = true) endFraction: Float,
        @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) fraction: Float
    ): Float {
        if (fraction < startFraction) return startValue
        if (fraction > endFraction) return endValue

        return lerp(startValue, endValue, (fraction - startFraction) / (endFraction - startFraction))
    }

    /**
     * Linearly interpolate between two values when the fraction is in a given range.
     */
    fun lerp(
        startValue: Int,
        endValue: Int,
        @FloatRange(
            from = 0.0,
            fromInclusive = true,
            to = 1.0,
            toInclusive = false
        ) startFraction: Float,
        @FloatRange(from = 0.0, fromInclusive = false, to = 1.0, toInclusive = true) endFraction: Float,
        @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) fraction: Float
    ): Int {
        if (fraction < startFraction) return startValue
        if (fraction > endFraction) return endValue

        return lerp(startValue, endValue, (fraction - startFraction) / (endFraction - startFraction))
    }

    /**
     * Linearly interpolate between two colors when the fraction is in a given range.
     */
    @ColorInt
    fun lerpArgb(
        @ColorInt startColor: Int,
        @ColorInt endColor: Int,
        @FloatRange(
            from = 0.0,
            fromInclusive = true,
            to = 1.0,
            toInclusive = false
        ) startFraction: Float,
        @FloatRange(from = 0.0, fromInclusive = false, to = 1.0, toInclusive = true) endFraction: Float,
        @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) fraction: Float
    ): Int {
        if (fraction < startFraction) return startColor
        if (fraction > endFraction) return endColor

        return ArgbEvaluatorCompat.getInstance().evaluate(
            (fraction - startFraction) / (endFraction - startFraction),
            startColor,
            endColor
        )
    }

    /**
     * Coerce the receiving Float between inputMin and inputMax and linearly interpolate to the
     * outputMin to outputMax scale. This function is able to handle ranges which span negative and
     * positive numbers.
     *
     * This differs from [lerp] as the input values are not required to be between 0 and 1.
     */
    fun Float.normalize(
        inputMin: Float,
        inputMax: Float,
        outputMin: Float,
        outputMax: Float
    ): Float {
        if (this < inputMin) {
            return outputMin
        } else if (this > inputMax) {
            return outputMax
        }

        return outputMin * (1 - (this - inputMin) / (inputMax - inputMin)) +
                outputMax * ((this - inputMin) / (inputMax - inputMin))
    }
}