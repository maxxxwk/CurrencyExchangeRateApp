package com.example.currencyexchangerateapp.common.resurces

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceManager @Inject constructor(private val context: Context) {

    fun getString(@StringRes resId: Int): String = context.getString(resId)

}
