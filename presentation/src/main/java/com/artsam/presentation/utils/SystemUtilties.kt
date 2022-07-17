package com.artsam.presentation.utils

import android.os.Handler
import android.os.Looper

fun delay(miliseconds: Long, fn: () -> Unit){
    Handler(Looper.getMainLooper()).postDelayed(fn, miliseconds)
}