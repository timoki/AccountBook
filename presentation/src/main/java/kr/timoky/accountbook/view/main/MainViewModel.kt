package kr.timoky.accountbook.view.main

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.timoky.accountbook.NavigationDirections
import kr.timoky.accountbook.base.BaseViewModel
import kr.timoky.domain.model.navi.NavigateType

class MainViewModel : BaseViewModel() {

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
}