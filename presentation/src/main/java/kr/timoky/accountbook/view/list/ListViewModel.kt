package kr.timoky.accountbook.view.list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kr.timoky.accountbook.base.BaseViewModel
import kr.timoky.domain.model.ExpenseModel
import kr.timoky.domain.usecase.GetExpenseListUseCase
import java.sql.Date
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getExpenseItemListUseCase: GetExpenseListUseCase
) : BaseViewModel() {

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