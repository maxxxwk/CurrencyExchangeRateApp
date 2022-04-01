package com.example.currencyexchangerateapp.common.viewModel

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)