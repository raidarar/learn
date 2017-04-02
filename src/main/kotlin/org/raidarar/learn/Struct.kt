package org.raidarar.learn

import com.sun.jna.Structure

abstract class Struct : Structure() {

    @Volatile var released = false
        private set

    fun release() = apply {
        if (released) throw IllegalStateException("You must renew the struct before releasing it!")

        StructCache.map.put(javaClass, this)
        released = true
    }

    fun read(source: Source, address: Long) = apply {
        if (!released) throw IllegalStateException("You must release the struct before renewing it!")

        source.read(address, this)
        released = false
    }

    fun read(source: Source, address: Int) = read(source, address.toLong())

    fun write(source: Source, address: Long) = apply {
        source.write(address, this)
    }

    fun write(source: Source, address: Int) = write(source, address.toLong())

    override fun getFieldOrder(): List<String> = javaClass.declaredFields.map { it.name }

}