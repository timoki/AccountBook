package kr.timoky.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.timoky.domain.model.Result
import kr.timoky.domain.repository.ExpenseRepository
import javax.inject.Inject

class GetTotalMoneyUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<Result<Long>> {
        return expenseRepository.getTotalMoney()
    }
}