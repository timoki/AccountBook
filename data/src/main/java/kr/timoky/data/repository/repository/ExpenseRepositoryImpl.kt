package kr.timoky.data.repository.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kr.timoky.data.mapper.ObjectMapper.toEntity
import kr.timoky.data.mapper.ObjectMapper.toModel
import kr.timoky.data.repository.dataSource.LocalDataSource
import kr.timoky.domain.model.ExpenseItemModel
import kr.timoky.domain.model.Result
import kr.timoky.domain.model.ExpenseModel
import kr.timoky.domain.repository.ExpenseRepository
import kr.timoky.domain.utils.ErrorCallback
import java.sql.Date
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val errorCallback: ErrorCallback
) : ExpenseRepository {
    override fun getExpenseList(
        fromDate: Date?,
        toDate: Date?,
        fromMoney: Int?,
        toMoney: Int?,
        isConsumption: Boolean?,
        categoryId: Int?,
        memo: String?
    ): Flow<PagingData<ExpenseModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = true,
        )
    ) {
        localDataSource.getExpenseList()
    }.flow.map {
        it.map { entity ->
            entity.toModel()
        }
    }.flowOn(Dispatchers.IO)

    override fun getExpense(id: Int): Flow<Result<ExpenseModel>> =
        flow<Result<ExpenseModel>> {
            emit(
                Result.Success(
                    localDataSource.getExpense(id).toModel()
                )
            )
        }.catch { exception ->
            emit(Result.Error(exception.message))
        }.flowOn(Dispatchers.IO)

    override suspend fun insertExpenseItem(item: ExpenseItemModel) = withContext(Dispatchers.IO) {
        localDataSource.insertExpenseItem(item.toEntity())
    }

    override suspend fun deleteExpenseItem(item: ExpenseItemModel) = withContext(Dispatchers.IO) {
        localDataSource.deleteExpenseItem(item.toEntity())
    }
}