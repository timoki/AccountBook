package kr.timoky.accountbook.view.list

import android.view.View
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kr.timoky.accountbook.R
import kr.timoky.accountbook.base.BaseFragment
import kr.timoky.accountbook.base.MyLoadStateAdapter
import kr.timoky.accountbook.databinding.FragmentListBinding
import kr.timoky.accountbook.utils.observeOnLifecycleStop
import kr.timoky.accountbook.view.list.adapter.ExpenseListAdapter
import kr.timoky.domain.model.ExpenseModel
import kr.timoky.domain.model.Result

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding, ListViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    private val adapter: ExpenseListAdapter by lazy {
        ExpenseListAdapter()
    }

    override fun init(): Unit = with(binding) {
        refreshListener = this@ListFragment
        vm = viewModel

        listRv.adapter = adapter.withLoadStateFooter(
            MyLoadStateAdapter {
                adapter.retry()
            }
        )
    }

    override fun initListener() {
        adapter.setOnItemClickListener(object : ExpenseListAdapter.ItemListener {
            override fun onRootClick(item: ExpenseModel) {

            }
        })

        adapter.loadStateFlow.observeOnLifecycleStop(viewLifecycleOwner) {
            setShimmer(false)

            binding.isResult = when {
                it.refresh is LoadState.Loading -> {
                    setShimmer(true)
                    true
                }

                it.refresh is LoadState.Error -> {
                    binding.noResult.errorString = getString(R.string.no_history_error)
                    false
                }

                it.refresh is LoadState.NotLoading && adapter.itemCount == 0 -> {
                    binding.noResult.errorString = getString(R.string.no_history_account)
                    false
                }

                else -> {
                    true
                }
            }
        }
    }

    override fun initViewModelCallback(): Unit = with(viewModel) {
        getExpenseList().observeOnLifecycleStop(viewLifecycleOwner) {
            adapter.submitData(it)
            getTotalMoney()
        }

        getTotalMoney().observeOnLifecycleStop(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }

                is Result.Success -> {
                    binding.totalMoney = result.data
                }

                is Result.Error -> {

                }

                else -> {

                }
            }
        }
    }

    private fun setShimmer(isStart: Boolean) {
        binding.shimmer.apply {
            if (isStart) {
                visibility = View.VISIBLE
                startShimmer()
            } else {
                visibility = View.GONE
                stopShimmer()
            }
        }
    }

    override fun onRefresh() {
        binding.swipe.isRefreshing = false
        binding.noResultSwipe.isRefreshing = false
        adapter.refresh()
    }
}