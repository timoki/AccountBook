package kr.timoky.accountbook.view.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kr.timoky.accountbook.R
import kr.timoky.accountbook.base.adapter.AdapterItemListener
import kr.timoky.accountbook.databinding.ActivityMainBinding
import kr.timoky.accountbook.utils.Common.showSnackBar
import kr.timoky.accountbook.utils.observeInLifecycleDestroy
import kr.timoky.accountbook.utils.observeInLifecycleStop
import kr.timoky.accountbook.utils.observeOnLifecycleDestroy
import kr.timoky.accountbook.view.navi.*
import kr.timoky.domain.model.navi.NavigateType

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    NavController.OnDestinationChangedListener {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by viewModels()

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    private val bottomNavDrawer: MainBottomDrawerFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.bottom_nav_drawer) as MainBottomDrawerFragment
    }

    private val currentNavigationFragment: Fragment?
        get() = navHostFragment.childFragmentManager.fragments.first()

    private var backKeyPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        initViewModelCallback()
    }

    private fun init() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        navController.addOnDestinationChangedListener(this)

        bottomNavDrawer.apply {
            addOnSlideAction(HalfClockwiseRotateSlideAction(binding.bottomAppbarArrow))
            addOnSlideAction(AlphaSlideAction(binding.bottomAppbarTitle, true))
            addOnStateChangedAction(ShowHideFabStateAction(binding.fab))
            addOnStateChangedAction(ChangeSettingsMenuStateAction { showSettings ->
                /*binding.bottomAppbar.replaceMenu(if (showSettings) {
                    R.menu.bottom_app_bar_settings_menu
                } else {
                    getBottomAppBarMenuForDestination()
                })*/
            })

            addNavigationListener(object : AdapterItemListener {
                override fun onRootClick(arg: Any) {
                    arg as NavigateType
                    currentNavigationFragment?.let {
                        if (it::class.simpleName != arg.fragmentName) {
                            viewModel.fragmentNavigationSetting(arg)
                        }
                    }
                }
            })
        }

        binding.bottomAppbar.apply {
            setNavigationOnClickListener {
                bottomNavDrawer.toggle()
            }
            //setOnMenuItemClickListener(this@MainActivity)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.previousBackStackEntry == null || !navController.popBackStack()) {
                    if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                        backKeyPressedTime = System.currentTimeMillis()
                        showSnackBar(
                            binding.navHostFragmentContainer,
                            getString(R.string.main_back_pressed)
                        )
                    } else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                        finish()
                    }
                }
            }
        })
    }

    private fun initViewModelCallback() = with(viewModel) {
        isLoading.observeOnLifecycleDestroy(this@MainActivity) { show ->
            if (show) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        }

        backStack.observeOnLifecycleDestroy(this@MainActivity) { back ->
            if (back) {
                popBackStack()
            } else {
                navigateUp()
            }
        }

        fabClickChannel.onEach {
            viewModel.fragmentNavigationSetting(NavigateType.AddExpense())
        }.observeInLifecycleDestroy(this@MainActivity)

        onBottomNavClickChannel.onEach {
            bottomNavDrawer.toggle()
        }.observeInLifecycleStop(this@MainActivity)

        navigateToChannel.onEach {
            when (it.first) {
                is NavigateType.AddExpense -> navigateToFab(it.second)
                else -> navigateToCompose(it.second)
            }
        }.observeInLifecycleStop(this@MainActivity)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.listFragment,
            R.id.calendarFragment,
            R.id.chartFragment -> {
                showBottomAppBar()
            }

            R.id.addExpenseFragment -> {
                hideBottomAppBar()
            }
        }
    }

    private fun showBottomAppBar() {
        binding.run {
            if (bottomAppbar.visibility == View.VISIBLE) return
            bottomAppbar.visibility = View.VISIBLE
            bottomAppbarTitle.visibility = View.VISIBLE
            bottomAppbar.performShow()
            fab.show()
        }
    }

    private fun hideBottomAppBar() {
        binding.run {
            bottomAppbar.performHide()
            // Get a handle on the animator that hides the bottom app bar so we can wait to hide
            // the fab and bottom app bar until after it's exit animation finishes.
            bottomAppbar.animate().setListener(object : AnimatorListenerAdapter() {
                var isCanceled = false

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    if (isCanceled) return

                    // Hide the BottomAppBar to avoid it showing above the keyboard
                    // when composing a new email.
                    bottomAppbar.visibility = View.GONE
                    fab.visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(animation: Animator) {
                    super.onAnimationCancel(animation)
                    isCanceled = true
                }
            })
        }
    }

    private fun navigateToCompose(directions: NavDirections) {
        currentNavigationFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = 300
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = 300
            }
        }

        navController.navigate(directions)
    }

    private fun navigateToFab(directions: NavDirections) {
        MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            duration = 300
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT

            startView = binding.fab
            endView = binding.navHostFragmentContainer

            addTarget(binding.navHostFragmentContainer)

            TransitionManager.beginDelayedTransition(
                findViewById(R.id.navHostFragmentContainer),
                this
            )
        }

        navController.navigate(directions)
    }
}