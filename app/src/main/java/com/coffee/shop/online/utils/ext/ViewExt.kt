package com.coffee.shop.online.utils.ext

import android.content.Context

fun Int.dpToPx(context: Context): Int =
    this*context.resources.displayMetrics.density.toInt()