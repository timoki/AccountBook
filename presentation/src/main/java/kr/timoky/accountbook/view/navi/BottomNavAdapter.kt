package kr.timoky.accountbook.view.navi

import kr.timoky.accountbook.base.adapter.BaseAdapter
import kr.timoky.accountbook.base.adapter.BaseViewHolder
import kr.timoky.accountbook.databinding.ItemMainBottomDrawerBinding
import kr.timoky.domain.model.navi.NavigateType

class BottomNavAdapter(itemClick: (NavigateType) -> Unit) :
    BaseAdapter<NavigateType, ItemMainBottomDrawerBinding>(itemClick) {

    override fun getHolder() = ViewHolder()

    inner class ViewHolder : BaseViewHolder(binding) {
        override fun bind(item: Any) {
            item as NavigateType
            binding.item = item
            binding.listener = listener
        }
    }
}