package org.raidarar.learn.win

import java.util.*
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.Psapi.MODULEINFO
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference
import com.sun.jna.platform.win32.Kernel32.INSTANCE as JNAKernel32
import com.sun.jna.platform.win32.Psapi.INSTANCE as JNAPsapi
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import org.raidarar.learn.Process
import org.raidarar.learn.win.Psapi.Companion.INSTANCE as Psapi

class WindowsProcess(override val id: Int, val handle: WinNT.HANDLE) : Process {

    private val modulesMap = Collections.synchronizedMap(Object2ObjectArrayMap<String, WindowsModule>())

    override fun loadModules() {
        modulesMap.clear()

        val modules = arrayOfNulls<WinDef.HMODULE>(4096)
        val needed = IntByReference()
        if (org.raidarar.learn.win.Psapi.INSTANCE.EnumProcessModulesEx(handle, modules, modules.size, needed)) {
            for (i in 0..needed.value / Native.getNativeSize(WinDef.HMODULE::class.java)) {
                val hModule = modules[i] ?: continue
                val info = MODULEINFO()
                if (JNAPsapi.GetModuleInformation(handle, hModule, info, info.size())) {
                    val address = Pointer.nativeValue(hModule.pointer)
                    val module = WindowsModule(address, this, hModule, info)
                    modulesMap.put(module.name, module)
                }
            }
        }
    }

    override val modules: Map<String, WindowsModule> = modulesMap

    override fun read(address: Pointer, data: Pointer, bytesToRead: Int)
            = Kernel32.ReadProcessMemory(handle.pointer, address, data, bytesToRead, 0) > 0

    override fun write(address: Pointer, data: Pointer, bytesToWrite: Int)
            = Kernel32.WriteProcessMemory(handle.pointer, address, data, bytesToWrite, 0) > 0

}
