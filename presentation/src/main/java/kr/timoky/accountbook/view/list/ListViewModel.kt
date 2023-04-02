package kr.timoky.accountbook.view.list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.timoky.accountbook.base.BaseViewModel
import kr.timoky.domain.model.ExpenseModel
import kr.timoky.domain.model.Result
import kr.timoky.domain.usecase.GetExpenseListUseCase
import kr.timoky.domain.usecase.GetTotalMoneyUseCase
import java.sql.Date
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getTotalMoneyUseCase: GetTotalMoneyUseCase,
    private val getExpenseItemListUseCase: GetExpenseListUseCase
) : BaseViewModel() {

    fun getTotalMoney(): StateFlow<Result<Long>> =
        getTotalMoneyUseCase.invoke()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Result.Loading()
            )

    fun getExpenseList(
        fromDate: Date? = null,
        toDate: Date? = null,
        fromMoney: Int? = null,
        toMoney: Int? = null,
        isConsumption: Boolean? = null,
        categoryId: Int? = null,
        memo: String? = null,
    ): Flow<PagingData<ExpenseModel>> =
        getExpenseItemListUseCase.invoke(
            fromDate,
            toDate,
            fromMoney,
            toMoney,
            isConsumption,
            categoryId,
            memo
        ).cachedIn(viewModelScope)
}