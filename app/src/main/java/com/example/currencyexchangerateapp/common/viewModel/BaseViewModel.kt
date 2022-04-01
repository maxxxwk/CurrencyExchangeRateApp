package com.example.currencyexchangerateapp.common.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, INTENT> : ViewModel() {
    protected abstract val initialState: STATE
    private val _state: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val state: StateFlow<STATE>
        get() = _state.asStateFlow()

    private val interactions = MutableSharedFlow<INTENT>(replay = 1)

    init {
        interactions.onEach {
            _state.emit(handleIntent(it, _state.value))
        }.launchIn(viewModelScope)
    }

    protected abstract fun handleIntent(intent: INTENT, oldSTATE: STATE): STATE

    fun interact(intent: INTENT) = viewModelScope.launch {
        interactions.emit(intent)
    }
}