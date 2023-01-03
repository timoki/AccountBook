package kr.timoky.data.mapper

import kr.timoky.data.local.entity.CategoryEntity
import kr.timoky.data.local.entity.ExpenseEntity
import kr.timoky.data.local.entity.ExpenseItemEntity
import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.model.ExpenseItemModel
import kr.timoky.domain.model.base.ExpenseModel

object ObjectMapper {
    fun ExpenseEntity.toModel(): ExpenseModel = ExpenseModel(
        item = this.item.toModel(),
        category = this.category.toModel()
    )

    fun ExpenseItemEntity.toModel(): ExpenseItemModel = ExpenseItemModel(
        id = this._expenseId,
        date = this.date,
        money = this.money,
        isConsumption = this.isConsumption,
        categoryId = this.categoryId,
        memo = this.memo
    )

    fun ExpenseItemModel.toEntity(): ExpenseItemEntity = ExpenseItemEntity(
        date = this.date,
        money = this.money,
        isConsumption = this.isConsumption,
        categoryId = this.categoryId,
        memo = this.memo,
    )

    fun List<CategoryEntity>.toCategoryModel(): List<CategoryModel> = this.map {
        it.toModel()
    }

    fun CategoryEntity.toModel(): CategoryModel = CategoryModel(
        id = this._categoryId,
        name = this.name,
        isUse = this.isUse,
        color = this.color
    )

    fun CategoryModel.toEntity(): CategoryEntity = CategoryEntity(
        name = this.name,
        isUse = this.isUse,
        color = this.color
    )
}