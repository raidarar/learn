package org.raidarar.learn

import com.sun.jna.Pointer
import java.lang.Boolean
import java.lang.Byte
import java.lang.Double
import java.lang.Float
import java.lang.Short

inline fun Source.write(address: Long, bytes: Int, writeBody: Pointer.() -> Unit) {
    val resource = MemoryCache[bytes]
    resource.writeBody()
    write(address, resource)
}

inline fun Source.write(address: Int, bytes: Int, writeBody: Pointer.() -> Unit)
        = write(address.toLong(), bytes, writeBody)

inline operator fun <reified T : Any> Source.get(address: Long, offset: Long = 0) = when (T::class.java) {
    Byte::class.java -> byte(address, offset)
    Short::class.java -> short(address, offset)
    Character::class.java -> char(address, offset)
    Integer::class.java -> int(address, offset)
    java.lang.Long::class.java -> long(address, offset)
    Float::class.java -> float(address, offset)
    Double::class.java -> double(address, offset)
    Boolean::class.java -> boolean(address, offset)
    else -> throw IllegalArgumentException()
} as T

inline operator fun <reified T : Any> Source.get(address: Int, offset: Long = 0): T
        = get(address.toLong(), offset)