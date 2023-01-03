package kr.timoky.domain.model

import kr.timoky.domain.model.base.BaseModel

data class CategoryModel(
    val id: Int? = null,
    val name: String,
    val isUse: Boolean,
    val color: Int,
): BaseModel