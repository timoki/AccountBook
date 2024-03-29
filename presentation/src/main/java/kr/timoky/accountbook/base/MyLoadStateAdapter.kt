package kr.timoky.accountbook.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.timoky.accountbook.R
import kr.timoky.accountbook.databinding.ItemLoadStateViewBinding

class MyLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MyLoadStateAdapter.LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding: ItemLoadStateViewBinding =
            ItemLoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val errorString = parent.context.resources.getString(R.string.str_load_state_error)
        return LoadStateViewHolder(binding, errorString, retry)
    }

    inner class LoadStateViewHolder(
        private val binding: ItemLoadStateViewBinding,
        private val errorString: String,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.NotLoading && loadState.endOfPaginationReached) {
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
                return
            }

            if (loadState is LoadState.Error) {
                binding.errorMsg.text = errorString
            }

            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }
    }
}