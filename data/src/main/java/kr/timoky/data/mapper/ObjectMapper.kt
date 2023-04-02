package kr.timoky.data.mapper

import kr.timoky.data.local.entity.*
import kr.timoky.domain.model.*

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
        address = this.address?.toModel(),
        memo = this.memo,
    )

    fun ExpenseItemModel.toEntity(): ExpenseItemEntity = ExpenseItemEntity(
        date = this.date,
        money = this.money,
        isConsumption = this.isConsumption,
        categoryId = this.categoryId,
        address = this.address?.toEntity(),
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

    private fun AddressEntity.toModel(): AddressModel = AddressModel(
        address = this.address,
        addressSub = this.addressSub,
        lat = this.lat,
        lng = this.lng,
    )

    private fun AddressModel.toEntity(): AddressEntity = AddressEntity(
        address = this.address,
        addressSub = this.addressSub,
        lat = this.lat,
        lng = this.lng,
    )
}