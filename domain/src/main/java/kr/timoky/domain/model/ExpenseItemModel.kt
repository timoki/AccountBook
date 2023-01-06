package kr.timoky.domain.model

import kr.timoky.domain.model.base.BaseModel

data class ExpenseItemModel(
    val id: Int? = null,
    val date: Long,
    val money: Int,
    val isConsumption: Boolean,
    val categoryId: Int,
    val address: AddressModel,
    val memo: String,
): BaseModel {
    override val key: Int
        get() = id ?: 0
}