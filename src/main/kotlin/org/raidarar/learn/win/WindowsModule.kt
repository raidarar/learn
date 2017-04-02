package org.raidarar.learn.win

import com.sun.jna.Native
import com.sun.jna.platform.win32.Psapi.MODULEINFO
import com.sun.jna.platform.win32.WinDef
import org.raidarar.learn.Module

class WindowsModule(override val address: Long, override val process: WindowsProcess,
                    val handle: WinDef.HMODULE, val info: MODULEINFO) : Module {

    override val name by lazy {
        val baseName = ByteArray(256)
        Psapi.INSTANCE.GetModuleBaseNameA(process.handle, handle, baseName, baseName.size)
        Native.toString(baseName)!!
    }

    override val size = info.SizeOfImage.toLong()

}