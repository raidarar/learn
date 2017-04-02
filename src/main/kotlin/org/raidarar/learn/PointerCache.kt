package org.raidarar.learn

import com.sun.jna.Pointer

object PointerCache {

    private val pointer = ThreadLocal.withInitial { Pointer(0) }

    operator fun get(address: Long): Pointer {
        val pointer = pointer.get()
        Pointer.nativeValue(pointer, address)
        return pointer
    }

}