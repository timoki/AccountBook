package kr.timoky.accountbook.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
abstract class BaseViewModel: ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun showLoadingDialog() = viewModelScope.launch {
        _isLoading.emit(true)
    }

    fun hideLoadingDialog() = viewModelScope.launch{
        _isLoading.emit(false)
    }

    private val _isShowKeyboard = MutableSharedFlow<Boolean>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val isShowKeyboard: SharedFlow<Boolean> = _isShowKeyboard.asSharedFlow()

    fun hideKeyboard() = viewModelScope.launch {
        _isShowKeyboard.emit(true)
    }

    fun showKeyboard() = viewModelScope.launch {
        _isShowKeyboard.emit(false)
    }

    private val _backStack = MutableSharedFlow<Boolean>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val backStack: SharedFlow<Boolean> = _backStack.asSharedFlow()

    fun popBackStack() = viewModelScope.launch {
        _backStack.emit(true)
    }

    fun navigateUp() = viewModelScope.launch {
        _backStack.emit(false)
    }

    private val _showSnackBar = Channel<Pair<String, Int>>(Channel.CONFLATED)
    val showSnackBar = _showSnackBar.receiveAsFlow()

    fun showSnackBar(message: String, paddingVertical: Int) = viewModelScope.launch {
        _showSnackBar.send(message to paddingVertical)
    }
}