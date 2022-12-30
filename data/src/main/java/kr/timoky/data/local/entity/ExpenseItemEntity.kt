package kr.timoky.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "expenseItem",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        )
    ]
)
data class ExpenseItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Long,
    val money: Int,
    val isConsumption: Boolean,
    val categoryId: Int,
    val memo: String,
)
