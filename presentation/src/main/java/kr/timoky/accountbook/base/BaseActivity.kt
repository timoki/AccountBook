package kr.timoky.accountbook.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kr.timoky.accountbook.utils.observeOnLifecycleDestroy
import java.lang.reflect.ParameterizedType

@AndroidEntryPoint
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    abstract fun init()
    open fun initListener() {}
    open fun initViewModelCallback() = with(viewModel) {}
    open fun navigationBackStackCallback() {}

    protected lateinit var binding: VB
    abstract val viewModel: VM

    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>
    private val classVM = type.actualTypeArguments[1] as Class<VM>

    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = inflateMethod.invoke(null) as VB

        init()
        initListener()
        initViewModelCallback()
        navigationBackStackCallback()
        baseViewModelCallback()
    }

    private fun baseViewModelCallback() = with(viewModel) {
        isLoading.observeOnLifecycleDestroy(this@BaseActivity) { show ->
            if (show) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        }

        backStack.observeOnLifecycleDestroy(this@BaseActivity) { back ->
            if (back) {
                popBackStack()
            } else {
                navigateUp()
            }
        }
    }

    protected fun showLoadingDialog() {

    }

    protected fun hideLoadingDialog() {

    }

    override fun onDestroy() {
        hideLoadingDialog()
        super.onDestroy()
    }
}