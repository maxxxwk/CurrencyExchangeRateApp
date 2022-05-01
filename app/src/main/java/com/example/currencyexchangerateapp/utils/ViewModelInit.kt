package com.example.currencyexchangerateapp.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import javax.inject.Provider

@Composable
inline fun <reified VM : ViewModel> initViewModel(crossinline getViewModelProvider: () -> Provider<VM>): VM {
    return viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return getViewModelProvider().get() as T
            }
        }
    )
}
