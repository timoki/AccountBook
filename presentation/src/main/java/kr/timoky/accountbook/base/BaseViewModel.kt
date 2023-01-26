package kr.timoky.accountbook.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun showLoadingDialog() = viewModelScope.launch {
        _isLoading.emit(true)
    }

    fun hideLoadingDialog() = viewModelScope.launch {
        _isLoading.emit(false)
    }

    /*private val _showSnackBar = Channel<Pair<String, Int>>(Channel.CONFLATED)
    val showSnackBar = _showSnackBar.receiveAsFlow()

    fun showSnackBar(message: String, paddingVertical: Int) = viewModelScope.launch {
        _showSnackBar.send(message to paddingVertical)
    }*/
}