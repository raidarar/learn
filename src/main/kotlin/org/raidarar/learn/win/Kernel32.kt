package org.raidarar.learn.win

import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import com.sun.jna.Pointer

object Kernel32 {

    @JvmStatic
    external fun WriteProcessMemory(hProcess: Pointer, lpBaseAddress: Pointer, lpBuffer: Pointer,
                                    nSize: Int, lpNumberOfBytesWritten: Int): Long

    @JvmStatic
    external fun ReadProcessMemory(hProcess: Pointer, lpBaseAddress: Pointer, lpBuffer: Pointer,
                                   nSize: Int, lpNumberOfBytesRead: Int): Long

    init {
        Native.register(NativeLibrary.getInstance("Kernel32"))
    }

}