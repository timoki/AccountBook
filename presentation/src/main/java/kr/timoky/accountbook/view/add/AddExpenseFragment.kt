package kr.timoky.accountbook.view.add

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kr.timoky.accountbook.R
import kr.timoky.accountbook.base.BaseFragment
import kr.timoky.accountbook.base.MoneyType
import kr.timoky.accountbook.databinding.FragmentAddExpenseBinding
import kr.timoky.accountbook.utils.Common.showSnackBar
import kr.timoky.accountbook.utils.observeInLifecycleStop

@AndroidEntryPoint
class AddExpenseFragment : BaseFragment<FragmentAddExpenseBinding, AddExpenseViewModel>() {

    override fun init(): Unit = with(binding) {
        binding.lifecycleOwner = this@AddExpenseFragment
        binding.vm = viewModel

        binding.moneyInput.addOnEditTextAttachedListener {
            it.editText?.addTextChangedListener {
                viewModel.checkValid()
            }
        }
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
                        setSelectDateString(time)
                    }
                }

                AddExpenseClickType.CATEGORY -> {

                }

                AddExpenseClickType.ADDRESS -> {

                }

                AddExpenseClickType.ADD -> {
                    addExpense()
                    showSnackBar(requireActivity(), getString(R.string.str_add_expense_complete, if (moneyType.value == MoneyType.USE) "소비" else "입금"))
                    findNavController().popBackStack()
                }
            }
        }.observeInLifecycleStop(viewLifecycleOwner)
    }
}