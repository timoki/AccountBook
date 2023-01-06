package kr.timoky.domain.model

import kr.timoky.domain.model.base.BaseModel

data class AddressModel(
    val address: String?,
    val addressSub: String?,
    val lat: Float?,
    val lng: Float?,
): BaseModel {
    override val key: Int
        get() = 0
}