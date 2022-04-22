package com.example.currencyexchangerateapp.di

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import com.example.currencyexchangerateapp.MainActivity
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    @ExperimentalMaterialApi
    @FlowPreview
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}
