package kr.timoky.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import kr.timoky.data.local.entity.ExpenseEntity
import kr.timoky.data.local.entity.ExpenseItemEntity

@Dao
interface ExpenseDao {
    /*@Query(
        "SELECT expenseItem.id AS id, expenseItem.date AS date, expenseItem.money AS money, expenseItem.isConsumption AS isConsumption, category.* AS category, expenseItem.memo AS memo" +
                "FROM expenseItem " +
                "JOIN category ON category.id = expenseItem.categoryId" +
                "WHERE " +
                "(:fromDate IS NOT NULL AND :toDate IS NOT NULL AND (date BETWEEN :fromDate AND :toDate)) AND " +
                "(:fromMoney IS NOT NULL AND :toMoney IS NOT NULL AND (money BETWEEN :fromMoney AND :toMoney)) AND " +
                "(:isConsumption IS NOT NULL AND isConsumption = :isConsumption) AND " +
                "(:categoryId IS NOT NULL AND categoryId = :categoryId) AND " +
                "(:memo IS NOT NULL AND memo LIKE '%' || :memo || '%')"
    )*/
    @Query(
        "SELECT DISTINCT " +
                "ex.*, " +
                "ca.*" +
                "FROM " +
                "expenseItem AS ex " +
                "JOIN " +
                "category AS ca " +
                "ON " +
                "ca._categoryId = ex.categoryId "
    )
    fun getExpenseList(
        /*fromDate: Date? = null,
        toDate: Date? = null,
        fromMoney: Int? = null,
        toMoney: Int? = null,
        isConsumption: Boolean? = null,
        categoryId: Int? = null,
        memo: String? = null,*/
    ): PagingSource<Int, ExpenseEntity>

    @Query(
        "SELECT DISTINCT " +
                "ex.*, " +
                "ca.*" +
                "FROM " +
                "expenseItem AS ex " +
                "JOIN " +
                "category AS ca " +
                "ON " +
                "ca._categoryId = ex.categoryId " +
                "WHERE " +
                "ex._expenseId = :id"
    )
    fun getExpense(id: Int): ExpenseEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpenseItem(item: ExpenseItemEntity)

    @Delete
    fun deleteExpenseItem(item: ExpenseItemEntity)
}