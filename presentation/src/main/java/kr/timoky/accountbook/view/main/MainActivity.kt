package kr.timoky.accountbook.view.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import kr.timoky.accountbook.R
import kr.timoky.accountbook.base.BaseActivity
import kr.timoky.accountbook.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    override fun init() {

    }

    override fun initViewModelCallback() {

    }
}