package kr.timoky.accountbook.view.navi

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.use
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kr.timoky.accountbook.R
import kr.timoky.accountbook.base.adapter.AdapterItemListener
import kr.timoky.accountbook.base.BaseFragment
import kr.timoky.accountbook.databinding.FragmentMainBottomDrawerBinding
import kr.timoky.accountbook.utils.SemiCircleEdgeCutoutTreatment
import kr.timoky.accountbook.utils.observeInLifecycleStop
import kr.timoky.domain.model.navi.BottomNavItem
import kr.timoky.domain.model.navi.NavigateType

@AndroidEntryPoint
class MainBottomDrawerFragment :
    BaseFragment<FragmentMainBottomDrawerBinding, MainBottomDrawerViewModel>() {

    private val behavior: BottomSheetBehavior<FrameLayout> by lazy(LazyThreadSafetyMode.NONE) {
        BottomSheetBehavior.from(binding.backgroundContainer)
    }

    private val adapter: BottomNavAdapter by lazy {
        BottomNavAdapter() {
            close()
            navigationListeners.forEach { listener ->
                listener.onRootClick(it)
            }
        }
    }

    private val bottomSheetCallback = MainBottomDrawerCallback()
    private val navigationListeners: MutableList<AdapterItemListener> = mutableListOf()

    private val backgroundShapeDrawable: MaterialShapeDrawable by lazy {
        val backgroundContext = binding.backgroundContainer.context

        MaterialShapeDrawable().apply {
            fillColor = ColorStateList.valueOf(
                backgroundContext.obtainStyledAttributes(
                    intArrayOf(com.google.android.material.R.attr.colorPrimaryVariant)
                ).use {
                    it.getColor(0, Color.MAGENTA)
                }
            )
            elevation = resources.getDimension(R.dimen.dp_8)
            initializeElevationOverlay(requireContext())
        }
    }

    private val foregroundShapeDrawable: MaterialShapeDrawable by lazy(LazyThreadSafetyMode.NONE) {
        val foregroundContext = binding.foregroundContainer.context
        MaterialShapeDrawable().apply {
            fillColor = ColorStateList.valueOf(
                foregroundContext.obtainStyledAttributes(
                    intArrayOf(com.google.android.material.R.attr.colorOnPrimary)
                ).use {
                    it.getColor(0, Color.MAGENTA)
                }
            )
            elevation = resources.getDimension(R.dimen.dp_16)
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_NEVER
            initializeElevationOverlay(requireContext())
            shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                .setTopEdge(
                    SemiCircleEdgeCutoutTreatment(
                        resources.getDimension(R.dimen.dp_8),
                        resources.getDimension(R.dimen.dp_24),
                        0F,
                        resources.getDimension(R.dimen.dp_32)
                    )
                )
                .build()
        }
    }

    private val closeDrawerOnBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            close()
        }
    }

    override fun init(): Unit = with(binding) {
        requireActivity().onBackPressedDispatcher.addCallback(
            this@MainBottomDrawerFragment,
            closeDrawerOnBackPressed
        )

        vm = viewModel

        backgroundContainer.background = backgroundShapeDrawable
        foregroundContainer.background = foregroundShapeDrawable

        bottomSheetCallback.apply {
            addOnSlideAction(AlphaSlideAction(scrimView))
            addOnStateChangedAction(VisibilityStateAction(scrimView))
            addOnSlideAction(
                ForegroundSheetTransformSlideAction(
                    foregroundContainer,
                    foregroundShapeDrawable,
                    topImageView
                )
            )
            addOnStateChangedAction(ScrollToTopStateAction(navRecyclerView))

            addOnStateChangedAction(object : OnStateChangedAction {
                override fun onStateChanged(view: View, newState: Int) {
                    closeDrawerOnBackPressed.isEnabled = newState != STATE_HIDDEN
                }
            })
        }

        behavior.addBottomSheetCallback(bottomSheetCallback)
        behavior.state = STATE_HIDDEN

        navRecyclerView.adapter = adapter

        // TODO : Room 저장 후 사용하도록 변경(Config)
        val testList: ArrayList<NavigateType> = ArrayList()
        testList.add(
            NavigateType.List(
                BottomNavItem(
                    icon = R.drawable.baseline_list_black_24,
                    title = "리스트",
                    isChecked = true
                )
            )

        )

        testList.add(
            NavigateType.Calendar(
                BottomNavItem(
                    icon = R.drawable.baseline_calendar_today_black_24,
                    title = "달력",
                    isChecked = false
                )
            )
        )

        testList.add(
            NavigateType.Chart(
                BottomNavItem(
                    icon = R.drawable.outline_bar_chart_black_24,
                    title = "분석",
                    isChecked = false
                )
            )
        )

        adapter.submitList(testList.toList())
    }

    override fun initViewModelCallback(): Unit = with(viewModel) {
        closeChannel.onEach {
            close()
        }.observeInLifecycleStop(viewLifecycleOwner)
    }

    fun toggle() {
        if (behavior.state == STATE_HIDDEN) open()
        else if (
            behavior.state == BottomSheetBehavior.STATE_HALF_EXPANDED ||
            behavior.state == BottomSheetBehavior.STATE_EXPANDED ||
            behavior.state == BottomSheetBehavior.STATE_COLLAPSED
        ) close()
    }

    private fun open() {
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    fun close() {
        behavior.state = STATE_HIDDEN
    }

    fun addOnSlideAction(action: OnSlideAction) {
        bottomSheetCallback.addOnSlideAction(action)
    }

    fun addOnStateChangedAction(action: OnStateChangedAction) {
        bottomSheetCallback.addOnStateChangedAction(action)
    }

    fun addNavigationListener(listener: AdapterItemListener) {
        navigationListeners.add(listener)
    }
}