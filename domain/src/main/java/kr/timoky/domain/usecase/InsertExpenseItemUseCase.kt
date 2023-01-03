package kr.timoky.domain.usecase

import kr.timoky.domain.model.ExpenseItemModel
import kr.timoky.domain.repository.ExpenseRepository
import javax.inject.Inject

class InsertExpenseItemUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(
        item: ExpenseItemModel
    ) {
        expenseRepository.insertExpenseItem(item)
    }
}