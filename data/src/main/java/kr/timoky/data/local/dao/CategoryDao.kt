package kr.timoky.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.timoky.data.local.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getCategoryList(): List<CategoryEntity>

    @Query("SELECT * FROM category WHERE _categoryId = :id")
    fun getCategory(id: Int): CategoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(item: CategoryEntity)

    @Delete
    fun deleteCategory(item: CategoryEntity)
}