package kr.timoky.accountbook.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.flow.onEach
import kr.timoky.accountbook.R
import kr.timoky.accountbook.databinding.ActivityMainBinding
import kr.timoky.accountbook.utils.observeInLifecycleStop
import kr.timoky.accountbook.utils.observeOnLifecycleDestroy

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
        initViewModelCallback()
    }

    private fun init() {
        binding.viewModel = viewModel

        binding.bottomNav.apply {
            setupWithNavController(navController)
        }
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

        menuClickChannel.onEach {
            binding.drawer.openDrawer(GravityCompat.END)
        }.observeInLifecycleStop(this@MainActivity)
    }
}