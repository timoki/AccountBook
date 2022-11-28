package kr.timoky.accountbook.view.main

import android.os.Bundle
import kr.timoky.accountbook.R
import kr.timoky.accountbook.base.BaseActivity
import kr.timoky.accountbook.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun initViewModelCallback() {
        TODO("Not yet implemented")
    }
}