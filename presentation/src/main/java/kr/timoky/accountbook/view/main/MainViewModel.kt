package kr.timoky.accountbook.view.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.timoky.accountbook.base.BaseViewModel

class MainViewModel: BaseViewModel() {

    private val _menuClickChannel = Channel<Unit>(Channel.CONFLATED)
    val menuClickChannel = _menuClickChannel.receiveAsFlow()

    fun onMenuClick() = viewModelScope.launch {
        _menuClickChannel.send(Unit)
    }
}