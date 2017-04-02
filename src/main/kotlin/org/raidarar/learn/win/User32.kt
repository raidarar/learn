package org.raidarar.learn.win

import com.sun.jna.Native


object User32 {

    @JvmStatic
    external fun GetKeyState(nVirtKey: Int): Short

    init {
        Native.register("user32")
    }

}