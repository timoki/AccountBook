package kr.timoky.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val _categoryId: Int = 0,
    val name: String,
    val isUse: Boolean,
    val color: Int?,
)
