package kr.timoky.accountbook.view.add

import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.timoky.accountbook.base.BaseViewModel
import kr.timoky.accountbook.base.MoneyType
import kr.timoky.domain.model.AddressModel
import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.model.ExpenseItemModel
import kr.timoky.domain.usecase.InsertExpenseItemUseCase
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val insertExpenseItemUseCase: InsertExpenseItemUseCase
) : BaseViewModel() {

    private val _selectDateLong =
        MutableStateFlow(MaterialDatePicker.todayInUtcMilliseconds())
    val selectDateLong = _selectDateLong.asStateFlow()

    fun setSelectDateString(time: Long) = viewModelScope.launch {
        _selectDateLong.value = time
    }

    val money = MutableStateFlow(0)
    val moneyType = MutableStateFlow(MoneyType.USE)
    val category = MutableStateFlow<CategoryModel?>(null)
    val address = MutableStateFlow<AddressModel?>(null)
    val memo = MutableStateFlow("")

    private val _isSaveValid = MutableStateFlow(false)
    val isSaveValid = _isSaveValid.asStateFlow()

    private val _onClickChannel = Channel<AddExpenseClickType>(Channel.CONFLATED)
    val onClickChannel = _onClickChannel.receiveAsFlow()

    fun onClick(type: AddExpenseClickType) = viewModelScope.launch {
        _onClickChannel.send(type)
    }

    fun addExpense() = viewModelScope.launch {
        insertExpenseItemUseCase.invoke(
            ExpenseItemModel(
                date = selectDateLong.value,
                money = money.value,
                isConsumption = moneyType.value == MoneyType.USE,
                categoryId = category.value?.id ?: 1,
                address = address.value,
                memo = memo.value
            )
        )
    }

    fun checkValid() {
        _isSaveValid.value = money.value > 0
    }
}