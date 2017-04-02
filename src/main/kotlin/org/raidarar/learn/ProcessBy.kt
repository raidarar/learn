package org.raidarar.learn

import com.sun.jna.Platform
import org.raidarar.learn.win.Windows
import java.util.*

fun processByID(processID: Int): Process? = when {
    Platform.isWindows() || Platform.isWindowsCE() -> Windows.openProcess(processID)
    else -> null
}

fun processByName(processName: String): Process? = when {
    Platform.isWindows() || Platform.isWindowsCE() -> Windows.openProcess(processName)
    else -> null
}