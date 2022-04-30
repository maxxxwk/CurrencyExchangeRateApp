package com.example.currencyexchangerateapp.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(includes = [NetworkModule::class, DBModule::class])
class AppModule {

    @Provides
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @DispatcherDefault
    fun provideDispatcherDefault(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}
