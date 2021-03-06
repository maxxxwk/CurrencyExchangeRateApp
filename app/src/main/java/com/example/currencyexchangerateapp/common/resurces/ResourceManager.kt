package com.example.currencyexchangerateapp.common.resurces

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceManager @Inject constructor(@ApplicationContext private val context: Context) {

    fun getString(@StringRes resId: Int): String = context.getString(resId)

}
