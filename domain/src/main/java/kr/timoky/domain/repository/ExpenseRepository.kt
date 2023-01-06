package kr.timoky.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.timoky.domain.model.ExpenseItemModel
import kr.timoky.domain.model.Result
import kr.timoky.domain.model.ExpenseModel
import java.sql.Date

interface ExpenseRepository {
    fun getExpenseList(
        fromDate: Date? = null,
        toDate: Date? = null,
        fromMoney: Int? = null,
        toMoney: Int? = null,
        isConsumption: Boolean? = null,
        categoryId: Int? = null,
        memo: String? = null,
    ): Flow<PagingData<ExpenseModel>>

    fun getExpense(id: Int): Flow<Result<ExpenseModel>>

    suspend fun insertExpenseItem(item: ExpenseItemModel)

    suspend fun deleteExpenseItem(item: ExpenseItemModel)
}