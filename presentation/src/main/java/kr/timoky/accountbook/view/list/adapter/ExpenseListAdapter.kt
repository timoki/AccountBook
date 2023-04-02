package kr.timoky.accountbook.view.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.timoky.accountbook.databinding.ItemListBinding
import kr.timoky.accountbook.utils.Common.calculationTime
import kr.timoky.domain.model.ExpenseModel

class ExpenseListAdapter :
    PagingDataAdapter<ExpenseModel, ExpenseListAdapter.ViewHolder>(diffUtil) {

    private var listener: ItemListener? = null

    interface ItemListener {
        fun onRootClick(item: ExpenseModel)
    }

    fun setOnItemClickListener(itemClickListener: ItemListener) {
        this.listener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListBinding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listener?.let {
            holder.bind(getItem(position) ?: return, it)
        }
    }

    inner class ViewHolder(
        private val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            model: ExpenseModel,
            listener: ItemListener
        ) {
            binding.model = model
            model.item.apply {
                binding.date = calculationTime(date)
                binding.money = "${if (isConsumption) "-" else "+"}${money}원"
                binding.isConsumption = isConsumption
                binding.memo = memo
            }

            model.category?.let {
                binding.category = it.name
                binding.categoryColor = it.color
            }

            binding.listener = listener
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ExpenseModel>() {
            override fun areItemsTheSame(
                oldItem: ExpenseModel,
                newItem: ExpenseModel
            ): Boolean = oldItem.item.id == newItem.item.id

            override fun areContentsTheSame(
                oldItem: ExpenseModel,
                newItem: ExpenseModel
            ): Boolean = oldItem.item == newItem.item
        }
    }
}