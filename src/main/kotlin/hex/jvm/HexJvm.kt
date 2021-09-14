package hex.jvm

import hex.controller.commands.GlobalCommandRunnerProvider
import hex.controller.commands.hotkeys.HotkeyManagerProvider
import hex.controller.haccess.HexAccessControllerProvider
import hex.jvm.di.JvmDiHexCore
import hex.jvm.view.RootWindow
import sgui.swing.SwingComponentProvider
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main(args: Array<String>) {
    JvmDiHexCore.init()
    println("run")

    SwingUtilities.invokeAndWait {
        initUi()
    }
}

fun initUi() {
    val window = RootWindow(
        SwingComponentProvider,
        GlobalCommandRunnerProvider.runner,
        HexAccessControllerProvider.controller,
        HotkeyManagerProvider.manager)
    window.pack()
    window.isLocationByPlatform = true
    window.isVisible = true
    window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

}