package kr.timoky.data.local.dao

import androidx.room.*
import kr.timoky.data.local.entity.ExpenseItemEntity
import java.sql.Date

@Dao
interface ExpenseDao {
    @Query(
        "SELECT * FROM expenseItem WHERE " +
                "(:fromDate IS NOT NULL AND :toDate IS NOT NULL AND (date BETWEEN :fromDate AND :toDate)) AND " +
                "(:fromMoney IS NOT NULL AND :toMoney IS NOT NULL AND (money BETWEEN :fromMoney AND :toMoney)) AND " +
                "(:isConsumption IS NOT NULL AND isConsumption = :isConsumption) AND " +
                "(:categoryId IS NOT NULL AND categoryId = :categoryId) AND " +
                "(:memo IS NOT NULL AND memo LIKE '%' || :memo || '%')"
    )
    fun getExpenseItemList(
        fromDate: Date? = null,
        toDate: Date? = null,
        fromMoney: Int? = null,
        toMoney: Int? = null,
        isConsumption: Boolean? = null,
        categoryId: Int? = null,
        memo: String? = null,
    ): List<ExpenseItemEntity>

    @Query("SELECT * FROM expenseItem WHERE id = :id")
    fun getExpenseItem(id: Int): ExpenseItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpenseItem(item: ExpenseItemEntity)

    @Delete
    fun deleteExpenseItem(item: ExpenseItemEntity)
}