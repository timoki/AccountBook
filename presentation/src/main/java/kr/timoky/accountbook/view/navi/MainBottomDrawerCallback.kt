package kr.timoky.accountbook.view.navi

import android.annotation.SuppressLint
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.timoky.accountbook.utils.AnimationUtils.normalize
import kotlin.math.max

class MainBottomDrawerCallback : BottomSheetBehavior.BottomSheetCallback() {

    private val onSlideActions: MutableList<OnSlideAction> = mutableListOf()
    private val onStateChangedActions: MutableList<OnStateChangedAction> = mutableListOf()

    private var lastSlideOffset = -1.0f
    private var halfExpandedSlideOffset = Float.MAX_VALUE

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        if (halfExpandedSlideOffset == Float.MAX_VALUE) {
            calculateInitialHalfExpandedSlideOffset(bottomSheet)
        }

        lastSlideOffset = slideOffset
        val trueOffset = if (slideOffset <= halfExpandedSlideOffset) {
            slideOffset.normalize(
                -1F,
                halfExpandedSlideOffset,
                -1F,
                0F
            )
        } else {
            slideOffset.normalize(
                halfExpandedSlideOffset,
                1F,
                0F,
                1F
            )
        }

        onSlideActions.forEach {
            it.onSlide(bottomSheet, trueOffset)
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
            halfExpandedSlideOffset = lastSlideOffset
            onSlide(bottomSheet, lastSlideOffset)
        }

        onStateChangedActions.forEach {
            it.onStateChanged(bottomSheet, newState)
        }
    }

    @SuppressLint("PrivateResource")
    private fun calculateInitialHalfExpandedSlideOffset(view: View) {
        val parent = view.parent as CoordinatorLayout
        val behavior = BottomSheetBehavior.from(view)

        val halfExpandedOffset = parent.height * (1 - behavior.halfExpandedRatio)
        val peekHeightMin = parent.resources.getDimensionPixelSize(
            R.dimen.design_bottom_sheet_peek_height_min
        )
        val peek = max(peekHeightMin, parent.height - parent.width * 9 / 16)
        val collapsedOffset = max(
            parent.height - peek,
            max(0, parent.height - view.height)
        )
        halfExpandedSlideOffset = (collapsedOffset - halfExpandedOffset) / (parent.height - collapsedOffset)
    }

    fun addOnSlideAction(action: OnSlideAction): Boolean {
        return onSlideActions.add(action)
    }

    fun addOnStateChangedAction(action: OnStateChangedAction): Boolean {
        return onStateChangedActions.add(action)
    }
}