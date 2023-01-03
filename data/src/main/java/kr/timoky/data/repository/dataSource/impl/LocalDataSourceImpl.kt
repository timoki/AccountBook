package kr.timoky.data.repository.dataSource.impl

import androidx.paging.PagingSource
import kr.timoky.data.local.dao.CategoryDao
import kr.timoky.data.local.dao.ExpenseDao
import kr.timoky.data.local.entity.CategoryEntity
import kr.timoky.data.local.entity.ExpenseEntity
import kr.timoky.data.local.entity.ExpenseItemEntity
import kr.timoky.data.repository.dataSource.LocalDataSource
import java.sql.Date
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao
) : LocalDataSource {
    override fun getExpenseList(
        fromDate: Date?,
        toDate: Date?,
        fromMoney: Int?,
        toMoney: Int?,
        isConsumption: Boolean?,
        categoryId: Int?,
        memo: String?
    ): PagingSource<Int, ExpenseEntity> = expenseDao.getExpenseList(
        /*fromDate,
        toDate,
        fromMoney,
        toMoney,
        isConsumption,
        categoryId,
        memo*/
    )

    override suspend fun getExpense(id: Int): ExpenseEntity = expenseDao.getExpense(id)

    override suspend fun insertExpenseItem(item: ExpenseItemEntity) =
        expenseDao.insertExpenseItem(item)

    override suspend fun deleteExpenseItem(item: ExpenseItemEntity) =
        expenseDao.deleteExpenseItem(item)

    override suspend fun getCategoryList(): List<CategoryEntity> = categoryDao.getCategoryList()

    override suspend fun getCategory(id: Int): CategoryEntity = categoryDao.getCategory(id)

    override suspend fun insertCategory(item: CategoryEntity) = categoryDao.insertCategory(item)

    override suspend fun deleteCategory(item: CategoryEntity) = categoryDao.deleteCategory(item)
}