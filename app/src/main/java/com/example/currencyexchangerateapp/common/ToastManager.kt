package com.example.currencyexchangerateapp.common

import android.content.Context
import android.widget.Toast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToastManager @Inject constructor(private val context: Context) {
    fun showLongToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    fun showShortToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}