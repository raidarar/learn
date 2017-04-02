package org.raidarar.learn

interface Addressed {

    val address: Long

    fun offset(offset: Long) = address + offset

    fun offset(offset: Int) = offset(offset.toLong())

}