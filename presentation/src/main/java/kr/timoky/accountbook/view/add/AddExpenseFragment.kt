package kr.timoky.accountbook.view.add

import android.util.Log
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kr.timoky.accountbook.R
import kr.timoky.accountbook.base.BaseFragment
import kr.timoky.accountbook.databinding.FragmentAddExpenseBinding
import kr.timoky.accountbook.utils.observeInLifecycleStop
import java.util.*

@AndroidEntryPoint
class AddExpenseFragment : BaseFragment<FragmentAddExpenseBinding, AddExpenseViewModel>() {
    override fun init(): Unit = with(binding) {
        binding.vm = viewModel
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        Log.d("아외안되", "$menuVisible")
    }

    override fun initViewModelCallback(): Unit = with(viewModel) {
        onClickChannel.onEach {
            when (it) {
                AddExpenseClickType.DATE -> {
                    val datePicker = MaterialDatePicker.Builder
                        .datePicker()
                        .setSelection(selectDateLong.value)
                        .setTitleText(
                            getString(R.string.str_add_date_tile)
                        )
                        .build()

                    datePicker.show(childFragmentManager, "")
                    datePicker.addOnPositiveButtonClickListener { time ->
                        selectDateLong.value = time
                        Log.d("아외안되", "${selectDateLong.value} / $selectDateString")
                    }
                }

                AddExpenseClickType.MONEY_TYPE -> {

                }

                AddExpenseClickType.CATEGORY -> {

                }

                AddExpenseClickType.ADDRESS -> {

                }
            }
        }.observeInLifecycleStop(viewLifecycleOwner)
    }
}