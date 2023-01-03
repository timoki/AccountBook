package kr.timoky.accountbook.view.list

import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kr.timoky.accountbook.R
import kr.timoky.accountbook.base.BaseFragment
import kr.timoky.accountbook.base.MyLoadStateAdapter
import kr.timoky.accountbook.databinding.FragmentListBinding
import kr.timoky.accountbook.utils.observeOnLifecycleStop
import kr.timoky.accountbook.view.list.adapter.ExpenseListAdapter
import kr.timoky.domain.model.base.ExpenseModel

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding, ListViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    private val adapter: ExpenseListAdapter by lazy {
        ExpenseListAdapter()
    }

    override fun init(): Unit = with(binding) {
        //noResult.errorString = getString(R.string.no_history_account)
        noResult.refreshListener = this@ListFragment
        refreshListener = this@ListFragment

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
            binding.isResult = when {
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
        }
    }

    override fun onRefresh() {
        // 여기가 타지 않는 이유를 밝혀야함
        if (binding.isResult == true) {
            binding.swipe.isRefreshing = false
        } else {
            binding.noResult.noResultSwipe.isRefreshing = false
        }
    }
}