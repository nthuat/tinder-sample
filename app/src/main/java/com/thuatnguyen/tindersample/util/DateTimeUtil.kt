package com.thuatnguyen.tindersample.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate() = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date(this.times(1000)))

fun String.toDate() = this.toLong().toDate()