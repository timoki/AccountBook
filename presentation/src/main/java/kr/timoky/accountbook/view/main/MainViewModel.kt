package kr.timoky.accountbook.view.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.timoky.accountbook.NavigationDirections
import kr.timoky.accountbook.base.BaseViewModel
import kr.timoky.accountbook.utils.Common.showSnackBar
import kr.timoky.domain.model.CategoryModel
import kr.timoky.domain.model.Result
import kr.timoky.domain.model.navi.NavigateType
import kr.timoky.domain.usecase.GetCategoryUseCase
import kr.timoky.domain.usecase.InsertCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase
) : BaseViewModel() {

    var currentPageTitle = "리스트"

    private val _fabClickChannel = Channel<Unit>(Channel.CONFLATED)
    val fabClickChannel = _fabClickChannel.receiveAsFlow()

    fun fabClick() = viewModelScope.launch {
        _fabClickChannel.send(Unit)
    }

    private val _onBottomNavClickChannel = Channel<Unit>(Channel.CONFLATED)
    val onBottomNavClickChannel = _onBottomNavClickChannel.receiveAsFlow()

    fun onBottomNavClick() = viewModelScope.launch {
        _onBottomNavClickChannel.send(Unit)
    }

    private val _navigateToChannel = Channel<Pair<NavigateType, NavDirections>>(Channel.CONFLATED)
    val navigateToChannel = _navigateToChannel.receiveAsFlow()

    fun fragmentNavigationSetting(item: NavigateType) = viewModelScope.launch {
        item.getItem?.let {
            currentPageTitle = it.title
        }

        _navigateToChannel.send(
            item to
                    when (item) {
                        is NavigateType.List -> NavigationDirections.actionGlobalListFragment()
                        is NavigateType.Calendar -> NavigationDirections.actionGlobalCalendarFragment()
                        is NavigateType.Chart -> NavigationDirections.actionGlobalChartFragment()
                        is NavigateType.AddExpense -> NavigationDirections.actionGlobalAddExpenseFragment()
                        is NavigateType.Setting -> NavigationDirections.actionGlobalListFragment()
                    }
        )
    }

    fun checkDefaultCategory(
        id: Int
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            getCategoryUseCase.invoke(
                id
            ).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoadingDialog()
                    }

                    is Result.Success -> {
                        hideLoadingDialog()
                        if (result.data == null) {
                            insertCategoryUseCase.invoke(
                                CategoryModel()
                            )
                        }
                    }

                    else -> {
                        hideLoadingDialog()
                    }
                }
            }
        }
    }
}