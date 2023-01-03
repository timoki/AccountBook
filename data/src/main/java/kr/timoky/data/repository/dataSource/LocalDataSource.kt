package kr.timoky.data.repository.dataSource

import androidx.paging.PagingSource
import kr.timoky.data.local.entity.CategoryEntity
import kr.timoky.data.local.entity.ExpenseEntity
import kr.timoky.data.local.entity.ExpenseItemEntity
import java.sql.Date

interface LocalDataSource {
    fun getExpenseList(
        fromDate: Date? = null,
        toDate: Date? = null,
        fromMoney: Int? = null,
        toMoney: Int? = null,
        isConsumption: Boolean? = null,
        categoryId: Int? = null,
        memo: String? = null,
    ): PagingSource<Int, ExpenseEntity>

    suspend fun getExpense(id: Int): ExpenseEntity

    suspend fun insertExpenseItem(item: ExpenseItemEntity)

    suspend fun deleteExpenseItem(item: ExpenseItemEntity)

    suspend fun getCategoryList(): List<CategoryEntity>

    suspend fun getCategory(id: Int): CategoryEntity

    suspend fun insertCategory(item: CategoryEntity)

    suspend fun deleteCategory(item: CategoryEntity)
}