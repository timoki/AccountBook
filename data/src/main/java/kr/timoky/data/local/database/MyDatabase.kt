package kr.timoky.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.timoky.data.local.converter.RoomTypeConverters
import kr.timoky.data.local.dao.CategoryDao
import kr.timoky.data.local.dao.ExpenseDao
import kr.timoky.data.local.entity.CategoryEntity
import kr.timoky.data.local.entity.ExpenseItemEntity

@Database(
    entities = [
        ExpenseItemEntity::class,
        CategoryEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
}