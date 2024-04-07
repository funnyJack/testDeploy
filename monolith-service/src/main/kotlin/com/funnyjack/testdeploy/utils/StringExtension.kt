package com.funnyjack.testdeploy.utils

private val matchedChars = arrayOf(' ', '　', '\t', '\r', '\n')

fun String.fullTrim() = trim { it in matchedChars }
