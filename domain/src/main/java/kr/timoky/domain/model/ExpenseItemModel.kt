package kr.timoky.domain.model

data class ExpenseItemModel(
    val date: Long,
    val money: Int,
    val isConsumption: Boolean,
    val categoryId: Int,
    val memo: String,
)