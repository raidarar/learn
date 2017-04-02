package org.raidarar.learn.win

import com.sun.jna.Native
import com.sun.jna.platform.win32.Tlhelp32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.platform.win32.Kernel32.INSTANCE as JNAKernel32

object Windows {

    val DWORD_ZERO = WinDef.DWORD(0)

    fun openProcess(processID: Int, accessFlags: Int = WinNT.PROCESS_ALL_ACCESS): WindowsProcess {
        val handle = JNAKernel32.OpenProcess(accessFlags, true, processID)
        return WindowsProcess(processID, handle)
    }

    fun openProcess(processName: String): WindowsProcess? {
        val snapshot = JNAKernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPALL, DWORD_ZERO)
        val entry = Tlhelp32.PROCESSENTRY32.ByReference() // we reuse the same entry during iteration
        try {
            while (JNAKernel32.Process32Next(snapshot, entry)) {
                val fileName = Native.toString(entry.szExeFile)
                if (processName == fileName) return openProcess(entry.th32ProcessID.toInt())
            }
        } finally {
            JNAKernel32.CloseHandle(snapshot)
        }
        return null
    }

}