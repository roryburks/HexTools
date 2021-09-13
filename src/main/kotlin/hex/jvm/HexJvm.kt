package hex.jvm

import hex.jvm.view.RootWindow
import sgui.swing.SwingComponentProvider
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main(args: Array<String>) {
    println("run")

    SwingUtilities.invokeAndWait {
        initUi()
    }
}

fun initUi() {
    val window = RootWindow(SwingComponentProvider)
    window.pack()
    window.isLocationByPlatform = true
    window.isVisible = true
    window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

}