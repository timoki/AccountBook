package kr.timoky.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.timoky.domain.model.base.ExpenseModel
import kr.timoky.domain.repository.ExpenseRepository
import java.sql.Date
import javax.inject.Inject

class GetExpenseListUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(
        fromDate: Date? = null,
        toDate: Date? = null,
        fromMoney: Int? = null,
        toMoney: Int? = null,
        isConsumption: Boolean? = null,
        categoryId: Int? = null,
        memo: String? = null,
    ): Flow<PagingData<ExpenseModel>> {
        return expenseRepository.getExpenseList(
            fromDate,
            toDate,
            fromMoney,
            toMoney,
            isConsumption,
            categoryId,
            memo
        )
    }
}