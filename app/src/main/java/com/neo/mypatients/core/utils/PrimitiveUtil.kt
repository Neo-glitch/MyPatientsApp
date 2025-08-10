package com.neo.mypatients.core.utils

import android.content.res.Resources
import android.util.TypedValue
import kotlin.random.Random

val Long?.orZero
    get() = this ?: 0L

val Double?.orZero
    get() = this ?: 0.0

val Int?.orZero
    get() = this ?: 0

val Float?.orZero
    get() = this ?: 0F

val Boolean?.orFalse
    get() = this ?: false

val String?.orEmpty
    get() = this ?: ""

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

fun generateRandomInt() : Int = Random.nextInt(1_000_000, Int.MAX_VALUE)