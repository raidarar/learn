package org.raidarar.learn

import com.sun.jna.Platform
import org.raidarar.learn.win.User32

fun keyState(virtualKeyCode: Int): Int = when {
    Platform.isWindows() || Platform.isWindowsCE() -> User32.GetKeyState(virtualKeyCode).toInt()
    else -> throw UnsupportedOperationException("Unsupported platform (osType=${Platform.getOSType()}")
}

fun keyPressed(virtualKeyCode: Int) = keyState(virtualKeyCode) < 0

inline fun keyPressed(virtualKeyCode: Int, action: () -> Unit) = if (keyPressed(virtualKeyCode)) {
    action()
    true
} else false

fun keyReleased(virtualKeyCode: Int) = !keyPressed(virtualKeyCode)

inline fun keyReleased(virtualKeyCode: Int, action: () -> Unit) = if (keyReleased(virtualKeyCode)) {
    action()
    true
} else false