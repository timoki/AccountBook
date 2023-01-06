package kr.timoky.accountbook.view.add

import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.timoky.accountbook.base.BaseViewModel
import kr.timoky.domain.usecase.InsertExpenseItemUseCase
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val insertExpenseItemUseCase: InsertExpenseItemUseCase
) : BaseViewModel() {

    val selectDateLong = MutableStateFlow<Long>(MaterialDatePicker.todayInUtcMilliseconds())
    val selectDateString: String
        get() {
            return SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date(selectDateLong.value))
        }

    private val _onClickChannel = Channel<AddExpenseClickType>(Channel.CONFLATED)
    val onClickChannel = _onClickChannel.receiveAsFlow()

    fun onClick(type: AddExpenseClickType) = viewModelScope.launch {
        _onClickChannel.send(type)
    }
}