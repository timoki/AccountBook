package kr.timoky.domain.model

import kr.timoky.domain.model.base.BaseModel

data class CategoryModel(
    val id: Int? = null,
    val name: String = "",
    val isUse: Boolean = true,
    val color: Int? = null,
): BaseModel {
    override val key: Int
        get() = id ?: 0
}