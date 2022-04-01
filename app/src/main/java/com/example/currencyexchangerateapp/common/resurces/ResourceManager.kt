package com.example.currencyexchangerateapp.common.resurces

import android.content.Context
import javax.inject.Inject

class ResourceManager @Inject constructor(private val context: Context) {

    fun getString(resId: Int): String = context.getString(resId)

}