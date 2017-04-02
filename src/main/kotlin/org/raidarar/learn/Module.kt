package org.raidarar.learn

import com.sun.jna.Pointer

interface Module : Source, Addressed {

    val process: Process

    val name: String

    val size: Long

    override fun read(address: Pointer, data: Pointer, bytesToRead: Int)
            = process.read(address, data, bytesToRead)

    override fun write(address: Pointer, data: Pointer, bytesToWrite: Int)
            = process.write(address, data, bytesToWrite)

    override fun read(address: Long, data: Pointer, bytesToRead: Int)
            = process.read(offset(address), data, bytesToRead)

    override fun write(address: Long, data: Pointer, bytesToWrite: Int)
            = process.write(offset(address), data, bytesToWrite)

}