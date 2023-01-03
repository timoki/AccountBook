package kr.timoky.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.timoky.domain.model.Result
import kr.timoky.domain.model.base.ExpenseModel
import kr.timoky.domain.repository.ExpenseRepository
import javax.inject.Inject

class GetExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(
        id: Int
    ): Flow<Result<ExpenseModel>> {
        return expenseRepository.getExpense(id)
    }
}