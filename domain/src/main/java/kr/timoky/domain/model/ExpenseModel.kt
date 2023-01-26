package kr.timoky.domain.model

import kr.timoky.domain.model.base.BaseModel

data class ExpenseModel(
    val item: ExpenseItemModel,
    val category: CategoryModel?
): BaseModel {
    override val key: Int
        get() = item.id ?: 0
}