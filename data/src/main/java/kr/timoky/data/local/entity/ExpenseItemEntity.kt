package kr.timoky.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "expenseItem",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["_categoryId"],
            childColumns = ["categoryId"]
        )
    ]
)
data class ExpenseItemEntity(
    @PrimaryKey(autoGenerate = true)
    val _expenseId: Int = 1,
    val date: Long,
    val money: Int,
    val isConsumption: Boolean,
    val categoryId: Int,
    @Embedded
    val address: AddressEntity,
    val memo: String,
)
