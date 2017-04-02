package org.raidarar.learn


fun Byte.unsign() = java.lang.Byte.toUnsignedInt(this)

fun Short.unsign() = java.lang.Short.toUnsignedInt(this)

fun Int.unsign() = Integer.toUnsignedLong(this)