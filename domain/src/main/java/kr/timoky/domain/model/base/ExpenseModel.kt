package kr.timoky.domain.model.base

import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.model.ExpenseItemModel

data class ExpenseModel(
    val item: ExpenseItemModel,
    val category: CategoryModel
): BaseModel