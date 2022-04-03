package com.example.currencyexchangerateapp.common.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty

class ViewModelInitDelegate<T : ViewModel>(
    private val getViewModelFactory: () -> ViewModelProvider.Factory,
    private val javaClass: Class<T>
) {
    private lateinit var viewModelStoreOwner: ViewModelStoreOwner

    private val viewModel: T by lazy {
        if (!this::viewModelStoreOwner.isInitialized) {
            throw IllegalArgumentException("ViewModelInitDelegate needs ViewModelStoreOwner!")
        }
        ViewModelProvider(viewModelStoreOwner, getViewModelFactory())[javaClass]
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (thisRef is ViewModelStoreOwner) {
            if (!this::viewModelStoreOwner.isInitialized) {
                viewModelStoreOwner = thisRef
            }
        }
        return viewModel
    }
}

inline fun <reified T : ViewModel> initViewModel(noinline getViewModelFactory: () -> ViewModelProvider.Factory) =
    ViewModelInitDelegate(getViewModelFactory, T::class.java)
