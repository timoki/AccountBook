package kr.timoky.data.local.entity

import androidx.room.Embedded

data class ExpenseEntity(
    @Embedded
    val item: ExpenseItemEntity,
    @Embedded
    val category: CategoryEntity
)