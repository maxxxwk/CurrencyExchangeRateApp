package com.example.currencyexchangerateapp.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherDefault

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherIO
