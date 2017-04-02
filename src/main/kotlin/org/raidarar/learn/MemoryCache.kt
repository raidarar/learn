package org.raidarar.learn

import com.sun.jna.Memory
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap

object MemoryCache {

    const val CACHE_BYTE_MAX = 512

    private val map = ThreadLocal.withInitial { Int2ObjectArrayMap<Memory>(64) }

    operator fun get(size: Int, clear: Boolean = false): Memory {
        val map = map.get()

        var memory = map.get(size)
        if (memory == null) {
            memory = Memory(size.toLong())
            if (size <= CACHE_BYTE_MAX)
                map.put(size, memory)
        } else if (clear) memory.clear()
        return memory
    }

}