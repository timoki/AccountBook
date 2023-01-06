package kr.timoky.accountbook.view.navi

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.timoky.accountbook.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainBottomDrawerViewModel @Inject constructor(

): BaseViewModel() {
    private val _closeChannel = Channel<Unit>(Channel.CONFLATED)
    val closeChannel = _closeChannel.receiveAsFlow()

    fun onClose() = viewModelScope.launch {
        _closeChannel.send(Unit)
    }
}