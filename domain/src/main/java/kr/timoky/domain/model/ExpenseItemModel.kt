package kr.timoky.domain.model

import kr.timoky.domain.model.base.BaseModel

data class ExpenseItemModel(
    val id: Int? = null,
    val date: Long,
    val money: Int,
    val isConsumption: Boolean,
    val categoryId: Int,
    val memo: String,
): BaseModel