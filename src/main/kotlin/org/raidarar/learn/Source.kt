package org.raidarar.learn

import com.sun.jna.Memory
import com.sun.jna.Pointer

interface Source {

    fun read(address: Pointer, data: Pointer, bytesToRead: Int): Boolean

    fun read(address: Long, data: Pointer, bytesToRead: Int)
            = read(PointerCache[address], data, bytesToRead)

    fun read(address: Int, data: Pointer, bytesToRead: Int)
            = read(address.toLong(), data, bytesToRead)

    fun read(address: Long, memory: Memory, bytesToRead: Int = memory.size().toInt())
            = read(address, memory as Pointer, bytesToRead)

    fun read(address: Int, memory: Memory, bytesToRead: Int = memory.size().toInt())
            = read(address.toLong(), memory, bytesToRead)

    fun read(address: Long, struct: Struct, bytesToRead: Int = struct.size())
            = read(address, struct.pointer, bytesToRead)

    fun read(address: Int, struct: Struct, bytesToRead: Int = struct.size())
            = read(address.toLong(), struct, bytesToRead)

    fun read(address: Long, bytesToRead: Int, fromCache: Boolean = true): Memory? {
        val memory = if (fromCache) MemoryCache[bytesToRead] else Memory(bytesToRead.toLong())
        if (read(address, memory, bytesToRead)) return memory
        return null
    }

    fun read(address: Int, bytesToRead: Int, fromCache: Boolean = true)
            = read(address.toLong(), bytesToRead, fromCache)

    fun byte(address: Long, offset: Long = 0) = read(address, 1)!!.getByte(offset)

    fun byte(address: Int, offset: Long = 0) = byte(address.toLong(), offset)

    fun short(address: Long, offset: Long = 0) = read(address, 2)!!.getShort(offset)

    fun short(address: Int, offset: Long = 0) = short(address.toLong(), offset)

    fun char(address: Long, offset: Long = 0) = read(address, 2)!!.getChar(offset)

    fun char(address: Int, offset: Long = 0) = char(address.toLong(), offset)

    fun int(address: Long, offset: Long = 0) = read(address, 4)!!.getInt(offset)

    fun int(address: Int, offset: Long = 0) = int(address.toLong(), offset)

    fun long(address: Long, offset: Long = 0) = read(address, 8)!!.getLong(offset)

    fun long(address: Int, offset: Long = 0) = long(address.toLong(), offset)

    fun float(address: Long, offset: Long = 0) = read(address, 4)!!.getFloat(offset)

    fun float(address: Int, offset: Long = 0) = float(address.toLong(), offset)

    fun double(address: Long, offset: Long = 0) = read(address, 8)!!.getDouble(offset)

    fun double(address: Int, offset: Long = 0) = double(address.toLong(), offset)

    fun boolean(address: Long, offset: Long = 0) = byte(address, offset).unsign() > 0

    fun boolean(address: Int, offset: Long = 0) = boolean(address.toLong(), offset)

    fun write(address: Pointer, data: Pointer, bytesToWrite: Int): Boolean

    fun write(address: Long, data: Pointer, bytesToWrite: Int)
            = write(PointerCache[address], data, bytesToWrite)

    fun write(address: Int, data: Pointer, bytesToWrite: Int) = write(address.toLong(), data, bytesToWrite)

    fun write(address: Long, memory: Memory, bytesToWrite: Int = memory.size().toInt())
            = write(address, memory as Pointer, bytesToWrite)

    fun write(address: Int, memory: Memory, bytesToWrite: Int = memory.size().toInt())
            = write(address.toLong(), memory, bytesToWrite)

    fun write(address: Long, struct: Struct, bytesToWrite: Int = struct.size())
            = write(address, struct.pointer, bytesToWrite)

    fun write(address: Int, struct: Struct, bytesToWrite: Int = struct.size())
            = write(address.toLong(), struct, bytesToWrite)

    operator fun set(address: Long, value: Byte) = write(address, 1) {
        setByte(0, value)
    }

    operator fun set(address: Int, value: Byte) = set(address.toLong(), value)

    operator fun set(address: Long, value: Short) = write(address, 2) {
        setShort(0, value)
    }

    operator fun set(address: Int, value: Short) = set(address.toLong(), value)

    operator fun set(address: Long, value: Char) = write(address, 2) {
        setChar(0, value)
    }

    operator fun set(address: Int, value: Char) = set(address.toLong(), value)

    operator fun set(address: Long, value: Int) = write(address, 4) {
        setInt(0, value)
    }

    operator fun set(address: Int, value: Int) = set(address.toLong(), value)

    operator fun set(address: Long, value: Long) = write(address, 8) {
        setLong(0, value)
    }

    operator fun set(address: Int, value: Long) = set(address.toLong(), value)

    operator fun set(address: Long, value: Float) = write(address, 4) {
        setFloat(0, value)
    }

    operator fun set(address: Int, value: Float) = set(address.toLong(), value)

    operator fun set(address: Long, value: Double) = write(address, 8) {
        setDouble(0, value)
    }

    operator fun set(address: Int, value: Double) = set(address.toLong(), value)

    operator fun set(address: Long, value: Boolean) = set(address, (if (value) 1 else 0).toByte())

    operator fun set(address: Int, value: Boolean) = set(address.toLong(), value)

}